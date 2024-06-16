package nlu.hmuaf.android_bookapp.profile.activity;

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
import nlu.hmuaf.android_bookapp.profile.DarkModeUtil;

public class AddBankAccountActivity extends AppCompatActivity {
    private EditText editTextBankName;
    private EditText editTextBranchName;
    private EditText editTextAccountNumber;
    private EditText editTextAccountHolderName;
    private Switch switchDefaultAccount;
    private Button buttonComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_add_bank_account);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Bank Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button

        editTextBankName = findViewById(R.id.editTextBankName);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextAccountHolderName = findViewById(R.id.editTextAccountHolderName);
        switchDefaultAccount = findViewById(R.id.switchDefaultAccount);
        buttonComplete = findViewById(R.id.buttonComplete);

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String bankName = editTextBankName.getText().toString().trim();
                String branchName = editTextBranchName.getText().toString().trim();
                String accountNumber = editTextAccountNumber.getText().toString().trim();
                String accountHolderName = editTextAccountHolderName.getText().toString().trim();
                boolean isDefault = switchDefaultAccount.isChecked();

                // Check user input
                if (validateInput(bankName, branchName, accountNumber, accountHolderName)) {
                    // Return to BankAccountActivity and update the account list
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("BANK_NAME", bankName);
                    resultIntent.putExtra("BRANCH_NAME", branchName);
                    resultIntent.putExtra("ACCOUNT_NUMBER", accountNumber);
                    resultIntent.putExtra("ACCOUNT_HOLDER_NAME", accountHolderName);
                    resultIntent.putExtra("IS_DEFAULT", isDefault);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private boolean validateInput(String bankName, String branchName, String accountNumber, String accountHolderName) {
        if (bankName.isEmpty()) {
            editTextBankName.setError("Bank Name is required");
            editTextBankName.requestFocus();
            return false;
        }

        if (branchName.isEmpty()) {
            editTextBranchName.setError("Branch Name is required");
            editTextBranchName.requestFocus();
            return false;
        }

        if (accountNumber.isEmpty()) {
            editTextAccountNumber.setError("Account Number is required");
            editTextAccountNumber.requestFocus();
            return false;
        }

        if (!accountNumber.matches("\\d+")) {
            editTextAccountNumber.setError("Account Number must be numeric");
            editTextAccountNumber.requestFocus();
            return false;
        }

        if (accountHolderName.isEmpty()) {
            editTextAccountHolderName.setError("Account Holder Name is required");
            editTextAccountHolderName.requestFocus();
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
