package nlu.hmuaf.android_bookapp.user.cart_user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import nlu.hmuaf.android_bookapp.R;

public class AlertQuantityTo0Dialog extends Dialog {
    private Activity activity;
    private Button ok;

    public AlertQuantityTo0Dialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alret_quantity_to_0_dialog);

        ok= findViewById(R.id.buttonOkQuantityTo0);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



    }
}
