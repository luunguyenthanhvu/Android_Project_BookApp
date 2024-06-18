package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import nlu.hmuaf.android_bookapp.R;

public class ChangePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        EditText oldPassword = findViewById(R.id.old_password);
        EditText newPassword = findViewById(R.id.new_password);
        EditText confirmNewPassword = findViewById(R.id.confirm_new_password);
        Button changePasswordButton = findViewById(R.id.change_password_button);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle password change logic here
                String oldPwd = oldPassword.getText().toString();
                String newPwd = newPassword.getText().toString();
                String confirmNewPwd = confirmNewPassword.getText().toString();

                if (validatePassword(newPwd, confirmNewPwd)) {
                    // Call method to change password
                    Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatePassword(String newPwd, String confirmNewPwd) {
        if (newPwd.isEmpty() || confirmNewPwd.isEmpty()) {
            Toast.makeText(this, "Password fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPwd.length() <= 7) {
            Toast.makeText(this, "New password must be longer than 7 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPwd.equals(confirmNewPwd)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
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
