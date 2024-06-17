package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.Class.Address;

public class EditAddressActivity extends AppCompatActivity {

    private EditText editName, editPhone, editStreet, editCity, editDistrict, editWard;
    private Switch defaultSwitch;
    private Address address;
    private List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Eddit Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editStreet = findViewById(R.id.editStreet);
        editCity = findViewById(R.id.editCity);
        editDistrict = findViewById(R.id.editDistrict);
        editWard = findViewById(R.id.editWard);
        defaultSwitch = findViewById(R.id.defaultSwitch);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button saveButton = findViewById(R.id.saveButton);

        address = (Address) getIntent().getSerializableExtra("address");
        addressList = (List<Address>) getIntent().getSerializableExtra("addressList");

        if (address != null) {
            editName.setText(address.getUser().getFirstName() + " " + address.getUser().getLastName());
            editPhone.setText(address.getUser().getPhoneNum());
            editCity.setText(address.getCity());
            editDistrict.setText(address.getDistrict());
            editWard.setText(address.getWard());
            editStreet.setText(address.getStreet());
            defaultSwitch.setChecked(address.isDefault());
        }

        defaultSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (Address addr : addressList) {
                    addr.setDefault(false);
                }
                address.setDefault(true);
            }
        });

        saveButton.setOnClickListener(v -> {
            // Cập nhật địa chỉ và trả kết quả về AddressActivity
            String[] nameParts = editName.getText().toString().split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            address.getUser().setFirstName(firstName);
            address.getUser().setLastName(lastName);
            address.getUser().setPhoneNum(editPhone.getText().toString());
            address.setStreet(editStreet.getText().toString());
            address.setCity(editCity.getText().toString());
            address.setDistrict(editDistrict.getText().toString());
            address.setWard(editWard.getText().toString());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedAddress", address);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            // Xóa địa chỉ và trả kết quả về AddressActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("deletedAddress", address);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
