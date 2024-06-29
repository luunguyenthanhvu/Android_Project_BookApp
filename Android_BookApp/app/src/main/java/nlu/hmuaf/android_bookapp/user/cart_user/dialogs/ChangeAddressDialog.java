package nlu.hmuaf.android_bookapp.user.cart_user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;

public class ChangeAddressDialog extends Dialog {

    private Activity activity;
    private Address address;
    private EditText editTextStreet, editTextWard, editTextDistrict, editTextCity;

    private Button buttonOK;
    private Button buttonCancel;


    public ChangeAddressDialog(@NonNull Activity activity,Address address) {
        super(activity);

        this.activity = activity;
        this.address = address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_change_address_user_dialog);

        this.editTextStreet = (EditText) findViewById(R.id.edt_change_street);
        this.editTextWard = (EditText) findViewById(R.id.edt_change_ward);
        this.editTextDistrict = (EditText) findViewById(R.id.edt_change_district);
        this.editTextCity = (EditText) findViewById(R.id.edt_change_city);
        this.buttonOK = (Button) findViewById(R.id.btn_Ok);
        this.buttonCancel = (Button) findViewById(R.id.btn_Cancel);

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });
    }

    // User click "OK" button.
    private void buttonOKClick() {
        String street = this.editTextStreet.getText().toString();
        String ward = this.editTextWard.getText().toString();
        String district = this.editTextDistrict.getText().toString();
        String city = this.editTextCity.getText().toString();
        if (street == null || street.isEmpty() || ward == null || ward.isEmpty() || district == null || district.isEmpty() || city == null || city.isEmpty())
            {
                Toast.makeText(this.activity, "Bạn chưa điền đầy đủ thông tin. Vui lòng điền lại", Toast.LENGTH_LONG).show();
                return;
            }
            else{
                TextView textView = (TextView) this.activity.findViewById(R.id.tv_addressUser);
                textView.setText(street + ", " + ward + ", " + district + ", " + city);
                address.setStreet(street);
                address.setWard(ward);
                address.setDistrict(district);
                address.setCity(city);
                this.dismiss();
            }


        }



    // User click "Cancel" button.
    private void buttonCancelClick () {
        this.dismiss();
    }
}
