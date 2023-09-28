package hussein.nasereddine.areeba_challenge.interfaces;

import androidx.annotation.NonNull;

public interface FirebasePostRequestListener {
    void onFirebasePostRequestSuccess();
    void onFirebasePostRequestFailure(@NonNull String message);
}
