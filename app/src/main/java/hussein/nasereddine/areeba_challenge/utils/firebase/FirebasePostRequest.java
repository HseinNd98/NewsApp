package hussein.nasereddine.areeba_challenge.utils.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.dialogs.DialogLoader;
import hussein.nasereddine.areeba_challenge.interfaces.FirebasePostRequestListener;
import hussein.nasereddine.areeba_challenge.models.data_models.contact.ContactModel;
import hussein.nasereddine.areeba_challenge.utils.data.ValidationUtils;
import hussein.nasereddine.areeba_challenge.utils.network.NetworkUtils;

public class FirebasePostRequest {
    private final static String FIREBASE_DB_REF_CONTACT = "contact";

    /**
     * Sends a Write request using Firebase
     * Used to create a new contact object
     *
     * @param context           Activity context
     * @param contactModel      Contact model to be written in Firebase
     * @param listener          To respond with success/failure after posting data
     */
    public static void post(@NonNull Context context, @NonNull ContactModel contactModel, @NonNull FirebasePostRequestListener listener){
        // Check if has a valid internet connection
        if(!NetworkUtils.isNetworkAvailable(context)){
            listener.onFirebasePostRequestFailure(context.getString(R.string.msg_invalid_internet_connection));
            return;
        }

        // Validate email
        if(!ValidationUtils.isValidEmail(contactModel.getEmail())){
            listener.onFirebasePostRequestFailure(context.getString(R.string.msg_email_validation_error));
            return;
        }

        // Validate phone
        if(!ValidationUtils.isValidPhoneNumber(contactModel.getPhone())){
            listener.onFirebasePostRequestFailure(context.getString(R.string.msg_phone_validation_error));
            return;
        }

        // Start loader dialog
        DialogLoader.show(context);

        // Prepare contact map data
        final HashMap<String, Object> mapData = contactModel.toMap();

        // Prepare database reference with a unique ID child key
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                .child(FIREBASE_DB_REF_CONTACT)
                .child(generateRandomID());

        // Send data to Firebase
        dbRef.setValue(mapData, (error, ref) -> {
            // Hide loader
            DialogLoader.hide();

            // Handle error
            if(error != null){
                listener.onFirebasePostRequestFailure(error.getMessage());
                return;
            }

            // Success
            listener.onFirebasePostRequestSuccess();
        });
    }

    /**
     * @return Random string value to be used as an identifier for posting data
     */
    private static String generateRandomID(){
        return String.valueOf(System.currentTimeMillis());
    }
}
