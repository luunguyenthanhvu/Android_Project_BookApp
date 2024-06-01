package nlu.hmuaf.android_bookapp.profile.activity;

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

public class AddCardActivity extends AppCompatActivity {
    private EditText editTextCardNumber;
    private EditText editTextExpiryDate;
    private EditText editTextCVV;
    private EditText editTextCardHolderName;
    private EditText editTextBillingAddress;
    private EditText editTextPostalCode;
    private Button buttonComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm thẻ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Thiết lập nút quay lại

        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextExpiryDate = findViewById(R.id.editTextExpiryDate);
        editTextCVV = findViewById(R.id.editTextCVV);
        editTextCardHolderName = findViewById(R.id.editTextCardHolderName);
        editTextBillingAddress = findViewById(R.id.editTextBillingAddress);
        editTextPostalCode = findViewById(R.id.editTextPostalCode);
        buttonComplete = findViewById(R.id.buttonComplete);

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin người dùng nhập
                String cardNumber = editTextCardNumber.getText().toString().trim();
                String expiryDate = editTextExpiryDate.getText().toString().trim();
                String cvv = editTextCVV.getText().toString().trim();
                String cardHolderName = editTextCardHolderName.getText().toString().trim();
                String billingAddress = editTextBillingAddress.getText().toString().trim();
                String postalCode = editTextPostalCode.getText().toString().trim();

                // Kiểm tra thông tin nhập vào
                if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty() || cardHolderName.isEmpty() || billingAddress.isEmpty() || postalCode.isEmpty()) {
                    Toast.makeText(AddCardActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Quay lại BankAccountActivity và cập nhật danh sách thẻ
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("CARD_NUMBER", cardNumber);
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
