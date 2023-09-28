package hussein.nasereddine.areeba_challenge.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.interfaces.FirebasePostRequestListener;
import hussein.nasereddine.areeba_challenge.models.data_models.contact.ContactModel;
import hussein.nasereddine.areeba_challenge.utils.firebase.FirebasePostRequest;

@SuppressWarnings("FieldCanBeLocal")
public class ContactActivity extends AppCompatActivity implements TextWatcher {

    // Views
    private LinearLayoutCompat mRootView;
    private MaterialButton mBtnBack, mBtnSubmit;
    private TextInputEditText mName, mEmail, mPhone, mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initViews();
        listenForInputsChanges();

        mBtnSubmit.setOnClickListener(v -> submit());
        mBtnBack.setOnClickListener(v -> onBackPressed());
    }

    private void submit(){
        ContactModel contactModel = new ContactModel(getName(), getEmail(), getPhone(), getMessage());
        FirebasePostRequest.post(this, contactModel, new FirebasePostRequestListener() {
            @Override
            public void onFirebasePostRequestSuccess() {
                Snackbar.make(mRootView, "Message delivered. Thanks for contacting us!", 4000).show();

                mName.setText("");
                mEmail.setText("");
                mPhone.setText("");
                mMessage.setText("");
            }

            @Override
            public void onFirebasePostRequestFailure(@NonNull String message) {
                Snackbar.make(mRootView, message, 4000).show();
            }
        });
    }

    private void listenForInputsChanges(){
        mName.addTextChangedListener(this);
        mEmail.addTextChangedListener(this);
        mPhone.addTextChangedListener(this);
        mMessage.addTextChangedListener(this);
    }

    private void notifySubmitBtnForChanges(){
        final String name = getName();
        final String email = getEmail();
        final String phone = getPhone();
        final String message = getMessage();

        mBtnSubmit.setEnabled(!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !message.isEmpty());
    }

    private String getName(){
        return mName.getText() != null ? mName.getText().toString().trim() : "";
    }

    private String getEmail(){
        return mEmail.getText() != null ? mEmail.getText().toString().trim() : "";
    }

    private String getPhone(){
        return mPhone.getText() != null ? mPhone.getText().toString().trim() : "";
    }

    private String getMessage(){
        return mMessage.getText() != null ? mMessage.getText().toString().trim() : "";
    }

    private void initViews(){
        mRootView = findViewById(R.id.activity_contact_root);
        mBtnBack = findViewById(R.id.activity_contact_btn_back);
        mBtnSubmit = findViewById(R.id.activity_contact_btn_submit);
        mName = findViewById(R.id.activity_contact_name_text_input_edittext);
        mEmail = findViewById(R.id.activity_contact_email_text_input_edittext);
        mPhone = findViewById(R.id.activity_contact_phone_text_input_edittext);
        mMessage = findViewById(R.id.activity_contact_message_text_input_edittext);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        notifySubmitBtnForChanges();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}