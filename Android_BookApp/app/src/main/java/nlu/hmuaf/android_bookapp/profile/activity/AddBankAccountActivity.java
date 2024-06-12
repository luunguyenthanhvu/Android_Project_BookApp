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
                if (bankName.isEmpty() || branchName.isEmpty() || accountNumber.isEmpty() || accountHolderName.isEmpty()) {
                    Toast.makeText(AddBankAccountActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
