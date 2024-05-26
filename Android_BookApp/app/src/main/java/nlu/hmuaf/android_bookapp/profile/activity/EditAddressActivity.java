package nlu.hmuaf.android_bookapp.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.Class.Address;

public class EditAddressActivity extends AppCompatActivity {

    private EditText editName, editPhone, editStreet;
    private TextView cityDistrictWardTextView;
    private Switch defaultSwitch;
    private Address address;
    private List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sửa Địa chỉ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editStreet = findViewById(R.id.editStreet);
        cityDistrictWardTextView = findViewById(R.id.cityDistrictWardTextView);
        defaultSwitch = findViewById(R.id.defaultSwitch);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button saveButton = findViewById(R.id.saveButton);

        address = (Address) getIntent().getSerializableExtra("address");
        addressList = (List<Address>) getIntent().getSerializableExtra("addressList");

        if (address != null) {
            editName.setText(address.getUser().getFirstName() + " " + address.getUser().getLastName());
            editPhone.setText(address.getUser().getPhoneNum());
            cityDistrictWardTextView.setText(address.getCity() + ", " + address.getDistrict() + ", " + address.getWard());
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
            address.setCity(cityDistrictWardTextView.getText().toString().split(", ")[0]);
            address.setDistrict(cityDistrictWardTextView.getText().toString().split(", ")[1]);
            address.setWard(cityDistrictWardTextView.getText().toString().split(", ")[2]);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedAddress", address);
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
