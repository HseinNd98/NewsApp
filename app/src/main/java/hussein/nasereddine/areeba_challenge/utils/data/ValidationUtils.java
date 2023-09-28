package hussein.nasereddine.areeba_challenge.utils.data;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

public class ValidationUtils {

    public static boolean isValidEmail(@NonNull String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPhoneNumber(@NonNull String number) {
        return (!TextUtils.isEmpty(number) && Patterns.PHONE.matcher(number).matches());
    }
}
