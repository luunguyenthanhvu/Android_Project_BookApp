package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nlu.hmuaf.android_bookapp.R;

public class AddBankAccountActivity extends AppCompatActivity {
    private EditText editTextFullName, editTextIDNumber, editTextBankName, editTextBranchName, editTextAccountNumber;
    private Button buttonComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_add_bank_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Bank Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextIDNumber = findViewById(R.id.editTextIDNumber);
        editTextBankName = findViewById(R.id.editTextBankName);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        buttonComplete = findViewById(R.id.buttonComplete);

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    // Create a combined string and return it to the previous activity
                    String accountDetails = editTextBankName.getText().toString().trim() + " - " +
                            editTextBranchName.getText().toString().trim() + " - " +
                            editTextAccountNumber.getText().toString().trim();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("BANK_ACCOUNT_DETAILS", accountDetails);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private boolean validateInput() {
        // Validate Full Name
        if (!editTextFullName.getText().toString().trim().matches("^[a-zA-Z-\\s]+$")) {
            Toast.makeText(this, "Invalid full name. Only letters, spaces, and hyphens are allowed.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate ID Number
        if (!editTextIDNumber.getText().toString().trim().matches("^\\d{12}$")) {
            Toast.makeText(this, "Invalid ID number. It must be exactly 12 digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Bank Name
        if (!editTextBankName.getText().toString().trim().matches("^[a-zA-Z\\s]{3,}$")) {
            Toast.makeText(this, "Invalid bank name. It should be at least 3 characters long and only contain letters and spaces.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Branch Name
        if (!editTextBranchName.getText().toString().trim().matches("^[a-zA-Z0-9\\s]+$")) {
            Toast.makeText(this, "Invalid branch name. Only alphanumeric characters and spaces are allowed.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Account Number
        if (!editTextAccountNumber.getText().toString().trim().matches("^\\d{8,16}$")) {
            Toast.makeText(this, "Invalid account number. It must be between 8 and 16 digits.", Toast.LENGTH_SHORT).show();
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
