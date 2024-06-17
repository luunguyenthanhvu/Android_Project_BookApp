package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;

public class BankAccountActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ACCOUNT = 1;
    private static final int REQUEST_CODE_ADD_CARD = 2;
    private LinearLayout bankAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_bank_account);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank Account/Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Thiết lập nút quay lại

        bankAccountList = findViewById(R.id.bankAccountList);

        LinearLayout addNewCard = findViewById(R.id.addNewCard);
        LinearLayout addNewBankAccount = findViewById(R.id.addNewBankAccount);

        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankAccountActivity.this, AddCardActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CARD);
            }
        });

        addNewBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankAccountActivity.this, BankUserInforActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ACCOUNT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ACCOUNT && resultCode == RESULT_OK && data != null) {
            String bankName = data.getStringExtra("BANK_NAME");
            String accountNumber = data.getStringExtra("ACCOUNT_NUMBER");

            addBankAccountView(bankName, accountNumber);
        } else if (requestCode == REQUEST_CODE_ADD_CARD && resultCode == RESULT_OK && data != null) {
            String cardNumber = data.getStringExtra("CARD_NUMBER");

            addCardView(cardNumber);
        }
    }

    private void addBankAccountView(String bankName, String accountNumber) {
        View bankAccountView = getLayoutInflater().inflate(R.layout.profile_item_bank_account, bankAccountList, false);

        TextView textViewBankInfo = bankAccountView.findViewById(R.id.textViewBankInfo);
        Button buttonUnlink = bankAccountView.findViewById(R.id.buttonUnlink);

        String bankInfo = bankName + " *" + accountNumber.substring(accountNumber.length() - 4);
        textViewBankInfo.setText(bankInfo);

        buttonUnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankAccountList.removeView(bankAccountView);
            }
        });

        bankAccountList.addView(bankAccountView);
    }

    private void addCardView(String cardNumber) {
        View cardView = getLayoutInflater().inflate(R.layout.profile_item_bank_account, bankAccountList, false);

        TextView textViewCardInfo = cardView.findViewById(R.id.textViewBankInfo);
        Button buttonUnlink = cardView.findViewById(R.id.buttonUnlink);

        String cardInfo = "Card *" + cardNumber.substring(cardNumber.length() - 4);
        textViewCardInfo.setText(cardInfo);

        buttonUnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankAccountList.removeView(cardView);
            }
        });

        bankAccountList.addView(cardView);
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
