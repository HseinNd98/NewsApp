package hussein.nasereddine.areeba_challenge.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import hussein.nasereddine.areeba_challenge.R;

public class DialogLoader {
    private static Dialog dialog;

    public static void show(@NonNull Context context){
        try{
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_loader);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void hide(){
        try{
            if(dialog != null) dialog.dismiss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
