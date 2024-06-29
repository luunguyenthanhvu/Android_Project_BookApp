package nlu.hmuaf.android_bookapp.user.cart_user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import nlu.hmuaf.android_bookapp.R;

public class AlertExceedQuantityDialog extends Dialog {
    private Activity activity;
    private Button ok;

    public AlertExceedQuantityDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_exceed_quantity_dialog);

        ok= findViewById(R.id.buttonOkExceedQuantity);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



    }
}
