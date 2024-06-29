package nlu.hmuaf.android_bookapp.user.cart_user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;

public class AddNewAddressFromUserDialog extends Dialog {
    private Activity activity;
    private EditText editTextStreet, editTextWard, editTextDistrict, editTextCity;

    private Button buttonOK;
    private Button buttonCancel;
    private Address address = new Address();

    private OnAddressAddedListener listener;
    public interface OnAddressAddedListener {
        void onAddressAdded(Address address);
    }
    public AddNewAddressFromUserDialog(@NonNull Activity activity, OnAddressAddedListener listener) {
        super(activity);

        this.activity = activity;
        this.listener = listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_add_address_from_user_dialog);

        this.editTextStreet = (EditText) findViewById(R.id.edt_change_street);
        this.editTextWard = (EditText) findViewById(R.id.edt_change_ward);
        this.editTextDistrict = (EditText) findViewById(R.id.edt_change_district);
        this.editTextCity = (EditText) findViewById(R.id.edt_change_city);
        this.buttonCancel = (Button) findViewById(R.id.btn_Cancel);
        this.buttonOK = (Button) findViewById(R.id.btn_Ok);

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
    }
    private void buttonOKClick () {
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
            address.setStreet(street);
            address.setWard(ward);
            address.setDistrict(district);
            address.setCity(city);
            Toast.makeText(this.activity, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
            listener.onAddressAdded(address);
            this.dismiss();
        }

    }

    private void buttonCancelClick () {
        this.dismiss();
    }


}
