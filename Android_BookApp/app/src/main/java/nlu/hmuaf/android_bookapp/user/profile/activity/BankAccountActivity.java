package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.user.profile.adapter.BankAccountAdapter;
import nlu.hmuaf.android_bookapp.user.profile.adapter.CardAdapter;

public class BankAccountActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ACCOUNT = 1;
    private static final int REQUEST_CODE_ADD_CARD = 2;
    private RecyclerView recyclerViewBankAccounts;
    private RecyclerView recyclerViewCards;
    private BankAccountAdapter bankAccountAdapter;
    private CardAdapter cardAdapter;
    private List<String> bankAccountList;  // Updated to use List<String>
    private List<String> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_bank_account);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank Account/Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewBankAccounts = findViewById(R.id.recyclerViewBankAccounts);
        recyclerViewCards = findViewById(R.id.recyclerViewCards);
        recyclerViewBankAccounts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCards.setLayoutManager(new LinearLayoutManager(this));

        bankAccountList = new ArrayList<>();
        cardList = new ArrayList<>();

        bankAccountAdapter = new BankAccountAdapter(bankAccountList, new BankAccountAdapter.OnBankAccountUnlinkListener() {
            @Override
            public void onUnlink(String accountDetails) {
                bankAccountList.remove(accountDetails);
                bankAccountAdapter.notifyDataSetChanged();
            }
        });

        cardAdapter = new CardAdapter(cardList, new CardAdapter.OnCardUnlinkListener() {
            @Override
            public void onUnlink(String cardNumber) {
                cardList.remove(cardNumber);
                cardAdapter.notifyDataSetChanged();
            }
        });

        recyclerViewBankAccounts.setAdapter(bankAccountAdapter);
        recyclerViewCards.setAdapter(cardAdapter);

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
                Intent intent = new Intent(BankAccountActivity.this, AddBankAccountActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ACCOUNT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ACCOUNT && resultCode == RESULT_OK && data != null) {
            String accountDetails = data.getStringExtra("BANK_ACCOUNT_DETAILS");
            bankAccountList.add(accountDetails);
            bankAccountAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_ADD_CARD && resultCode == RESULT_OK && data != null) {
            String cardNumber = data.getStringExtra("CARD_NUMBER");
            cardList.add(cardNumber);
            cardAdapter.notifyDataSetChanged();
        }
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
