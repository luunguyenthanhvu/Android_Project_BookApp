package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.classess.UserDetails;

public class ProfileEditActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private EditText editName, editGender, editBirthdate, editPhone, editEmail;
    private static final int PICK_IMAGE_REQUEST = 1;
    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_profile_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage = findViewById(R.id.profile_image);
        editName = findViewById(R.id.edit_name);
        editGender = findViewById(R.id.edit_gender);
        editBirthdate = findViewById(R.id.edit_birthdate);
        editPhone = findViewById(R.id.edit_phone);
        editEmail = findViewById(R.id.edit_email);
        Button saveButton = findViewById(R.id.save_button);
        TextView editProfileImage = findViewById(R.id.edit_profile_image);
        ImageView arrowGender = findViewById(R.id.arrow_gender); // Ensure this ID is in your layout
        ImageView arrowBirthdate = findViewById(R.id.arrow_birthdate); // Ensure this ID is in your layout

        editProfileImage.setOnClickListener(v -> openImagePicker());
        saveButton.setOnClickListener(v -> saveProfile());

        // Initialize user details for testing
        userDetails = new UserDetails(1, "Tuong", "Minh", "mt@example.com", "2003-01-01", "1234567890", "Male", "");

        // Load profile data for testing
        loadProfileData();

        // Set up gender selection dialog
        arrowGender.setOnClickListener(v -> showGenderDialog());

        // Set up birthdate picker dialog
        arrowBirthdate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void loadProfileData() {
        editName.setText(String.format("%s %s", userDetails.getFirstName(), userDetails.getLastName()));
        editGender.setText(userDetails.getGender());
        editBirthdate.setText(userDetails.getDob());
        editPhone.setText(userDetails.getPhoneNum());
        editEmail.setText(userDetails.getEmail());
    }

    private void saveProfile() {
        String name = editName.getText().toString().trim();
        String gender = editGender.getText().toString().trim();
        String birthdate = editBirthdate.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (!validateInput(name, gender, birthdate, phone, email)) {
            return;
        }

        // Splitting full name into first name and last name
        String[] nameParts = name.split(" ", 2);
        if (nameParts.length == 2) {
            userDetails.setFirstName(nameParts[0]);
            userDetails.setLastName(nameParts[1]);
        } else if (nameParts.length == 1) {
            userDetails.setFirstName(nameParts[0]);
            userDetails.setLastName("");
        }

        userDetails.setGender(gender);
        userDetails.setDob(birthdate);
        userDetails.setPhoneNum(phone);
        userDetails.setEmail(email);

        // Set result with updated user details
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_user", userDetails);
        setResult(RESULT_OK, resultIntent);

        // Finish activity
        finish();

        // For demonstration, we'll just show a toast
        Toast.makeText(this, R.string.info_saved, Toast.LENGTH_SHORT).show();
    }

    private boolean validateInput(String name, String gender, String birthdate, String phone, String email) {
        if (name.isEmpty()) {
            editName.setError(getString(R.string.error_name_required));
            editName.requestFocus();
            return false;
        }

        if (gender.isEmpty()) {
            editGender.setError(getString(R.string.error_gender_required));
            editGender.requestFocus();
            return false;
        }

        if (birthdate.isEmpty()) {
            editBirthdate.setError(getString(R.string.error_birthdate_required));
            editBirthdate.requestFocus();
            return false;
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 10 || !phone.matches("\\d+")) {
            editPhone.setError(getString(R.string.error_invalid_phone));
            editPhone.requestFocus();
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.error_invalid_email));
            editEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void showGenderDialog() {
        String[] genders = {"Male", "Female", "Other"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_gender)
                .setItems(genders, (dialog, which) -> editGender.setText(genders[which]))
                .create()
                .show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> editBirthdate.setText(String.format("%04d-%02d-%02d", year1, month1 + 1, dayOfMonth)),
                year, month, day);
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profileImage.setImageURI(data.getData());
        }
    }
}
