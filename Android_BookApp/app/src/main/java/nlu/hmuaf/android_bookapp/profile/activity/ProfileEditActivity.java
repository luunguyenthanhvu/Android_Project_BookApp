package nlu.hmuaf.android_bookapp.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.Class.UserDetails;

public class ProfileEditActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private EditText editName, editBio, editGender, editBirthdate, editPhone, editEmail;
    private static final int PICK_IMAGE_REQUEST = 1;
    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage = findViewById(R.id.profile_image);
        editName = findViewById(R.id.edit_name);
        editGender = findViewById(R.id.edit_gender);
        editBirthdate = findViewById(R.id.edit_birthdate);
        editPhone = findViewById(R.id.edit_phone);
        editEmail = findViewById(R.id.edit_email);
        Button saveButton = findViewById(R.id.save_button);
        TextView editProfileImage = findViewById(R.id.edit_profile_image);

        editProfileImage.setOnClickListener(v -> openImagePicker());

        saveButton.setOnClickListener(v -> saveProfile());

        // Initialize user details for testing
        userDetails = new UserDetails(1, "Tuong", "Minh", "mt@example.com", "2003-01-01", "123456789", "Male", "");

        // Load profile data for testing
        loadProfileData();
    }

    private void loadProfileData() {
        editName.setText(userDetails.getFirstName() + " " + userDetails.getLastName());
        editGender.setText(userDetails.getGender());
        editBirthdate.setText(userDetails.getDob());
        editPhone.setText(userDetails.getPhoneNum());
        editEmail.setText(userDetails.getEmail());
    }

    private void saveProfile() {
        String name = editName.getText().toString();
        String gender = editGender.getText().toString();
        String birthdate = editBirthdate.getText().toString();
        String phone = editPhone.getText().toString();
        String email = editEmail.getText().toString();

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

        // For demonstration, we'll just show a toast
        Toast.makeText(this, "Information has been saved", Toast.LENGTH_SHORT).show();
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
