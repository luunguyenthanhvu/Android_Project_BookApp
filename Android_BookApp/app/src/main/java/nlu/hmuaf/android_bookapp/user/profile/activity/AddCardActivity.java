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
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setContentView(R.layout.profile_activity_add_card);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                // Get user input
                String cardNumber = editTextCardNumber.getText().toString().trim();
                String expiryDate = editTextExpiryDate.getText().toString().trim();
                String cvv = editTextCVV.getText().toString().trim();
                String cardHolderName = editTextCardHolderName.getText().toString().trim();
                String billingAddress = editTextBillingAddress.getText().toString().trim();
                String postalCode = editTextPostalCode.getText().toString().trim();

                // Check user input
                if (validateInput(cardNumber, expiryDate, cvv, cardHolderName, billingAddress, postalCode)) {
                    // Return to previous activity and update card list
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("CARD_NUMBER", cardNumber);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private boolean validateInput(String cardNumber, String expiryDate, String cvv, String cardHolderName, String billingAddress, String postalCode) {
        if (cardNumber.isEmpty()) {
            editTextCardNumber.setError("Card Number is required");
            editTextCardNumber.requestFocus();
            return false;
        }

        if (!isValidCardNumber(cardNumber)) {
            editTextCardNumber.setError("Invalid card number");
            editTextCardNumber.requestFocus();
            return false;
        }

        if (expiryDate.isEmpty()) {
            editTextExpiryDate.setError("Expiry Date is required");
            editTextExpiryDate.requestFocus();
            return false;
        }

        if (!isValidExpiryDate(expiryDate)) {
            editTextExpiryDate.setError("Invalid expiry date (MM/YY)");
            editTextExpiryDate.requestFocus();
            return false;
        }

        if (cvv.isEmpty()) {
            editTextCVV.setError("CVV is required");
            editTextCVV.requestFocus();
            return false;
        }

        if (!cvv.matches("\\d{3,4}")) {
            editTextCVV.setError("CVV must be 3 or 4 digits");
            editTextCVV.requestFocus();
            return false;
        }

        if (cardHolderName.isEmpty()) {
            editTextCardHolderName.setError("Card Holder Name is required");
            editTextCardHolderName.requestFocus();
            return false;
        }

        if (billingAddress.isEmpty()) {
            editTextBillingAddress.setError("Billing Address is required");
            editTextBillingAddress.requestFocus();
            return false;
        }
    // mã bưu chnh
        if (postalCode.isEmpty()) {
            editTextPostalCode.setError("Postal Code is required");
            editTextPostalCode.requestFocus();
            return false;
        }

        return true;
    }
    // nhập đủ 16 số
    private boolean isValidCardNumber(String cardNumber) {
        int nDigits = cardNumber.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNumber.charAt(i) - '0';
            if (isSecond) d = d * 2;
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    private boolean isValidExpiryDate(String expiryDate) {
        // Check if expiry date matches the format MM/YY
        Pattern pattern = Pattern.compile("(0[1-9]|1[0-2])/[0-9]{2}");
        Matcher matcher = pattern.matcher(expiryDate);
        return matcher.matches();
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
