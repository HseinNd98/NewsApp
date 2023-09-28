package hussein.nasereddine.areeba_challenge.models.data_models.contact;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class ContactModel {
    private final String name, email, phone, message;

    public ContactModel(@NonNull String name, @NonNull String email, @NonNull String phone, @NonNull String message) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("phone", phone);
        map.put("email", email);
        map.put("message", message);
        return map;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }
}
