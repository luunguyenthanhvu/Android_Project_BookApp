package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.Class.Address;
import nlu.hmuaf.android_bookapp.user.profile.Class.User;

public class EditAddressActivity extends AppCompatActivity {

    private EditText editName, editPhone, editStreet, editCity, editDistrict, editWard;
    private Switch defaultSwitch;
    private Address address;
    private boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Address");
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
        isNew = (address == null);

        if (!isNew) {
            editName.setText(address.getUser().getFirstName() + " " + address.getUser().getLastName());
            editPhone.setText(address.getUser().getPhoneNum());
            editCity.setText(address.getCity());
            editDistrict.setText(address.getDistrict());
            editWard.setText(address.getWard());
            editStreet.setText(address.getStreet());
            defaultSwitch.setChecked(address.isDefault());
        } else {
            address = new Address();
            address.setUser(new User());
            defaultSwitch.setChecked(false);
        }

        saveButton.setOnClickListener(v -> {
            if (validateInput()) {
                String[] nameParts = editName.getText().toString().split(" ");
                String firstName = nameParts.length > 0 ? nameParts[0] : "";
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                address.getUser().setFirstName(firstName);
                address.getUser().setLastName(lastName);
                address.getUser().setPhoneNum(editPhone.getText().toString());
                address.setCity(editCity.getText().toString());
                address.setDistrict(editDistrict.getText().toString());
                address.setWard(editWard.getText().toString());
                address.setStreet(editStreet.getText().toString());
                address.setDefault(defaultSwitch.isChecked());

                Intent resultIntent = new Intent();
                resultIntent.putExtra(isNew ? "newAddress" : "updatedAddress", address);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        deleteButton.setVisibility(isNew ? View.GONE : View.VISIBLE);
        deleteButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("deletedAddress", address);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private boolean validateInput() {
        if (editName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!editName.getText().toString().matches("^[\\p{L} .'-]+$")) {
            Toast.makeText(this, "Invalid name. Only letters and certain special characters (spaces, hyphens) are allowed.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editPhone.getText().toString().isEmpty() || !editPhone.getText().toString().matches("^\\d{10}$")) {
            Toast.makeText(this, "Invalid phone number. It must be exactly 10 digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editCity.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "City is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editDistrict.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "District is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editWard.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ward is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editStreet.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Street is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
