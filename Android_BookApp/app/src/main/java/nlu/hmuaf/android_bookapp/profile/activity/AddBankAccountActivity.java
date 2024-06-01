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
        setContentView(R.layout.activity_add_bank_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm tài khoản ngân hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Thiết lập nút quay lại

        editTextBankName = findViewById(R.id.editTextBankName);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextAccountHolderName = findViewById(R.id.editTextAccountHolderName);
        switchDefaultAccount = findViewById(R.id.switchDefaultAccount);
        buttonComplete = findViewById(R.id.buttonComplete);

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin người dùng nhập
                String bankName = editTextBankName.getText().toString().trim();
                String branchName = editTextBranchName.getText().toString().trim();
                String accountNumber = editTextAccountNumber.getText().toString().trim();
                String accountHolderName = editTextAccountHolderName.getText().toString().trim();
                boolean isDefault = switchDefaultAccount.isChecked();

                // Kiểm tra thông tin nhập vào
                if (bankName.isEmpty() || branchName.isEmpty() || accountNumber.isEmpty() || accountHolderName.isEmpty()) {
                    Toast.makeText(AddBankAccountActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Quay lại BankAccountActivity và cập nhật danh sách tài khoản
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
