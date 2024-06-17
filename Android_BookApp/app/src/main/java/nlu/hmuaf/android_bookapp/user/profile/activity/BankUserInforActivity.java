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

public class BankUserInforActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_BANK_ACCOUNT = 2;

    private EditText editTextFullName;
    private EditText editTextCCCD;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_bank_user_infor);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextCCCD = findViewById(R.id.editTextCCCD);
        buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent intent = new Intent(BankUserInforActivity.this, AddBankAccountActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_ADD_BANK_ACCOUNT);
                }
            }
        });
    }

    private boolean validateInput() {
        String fullName = editTextFullName.getText().toString().trim();
        String cccd = editTextCCCD.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required");
            editTextFullName.requestFocus();
            return false;
        }

        if (cccd.isEmpty()) {
            editTextCCCD.setError("ID Number is required");
            editTextCCCD.requestFocus();
            return false;
        }

        if (!cccd.matches("\\d+")) {
            editTextCCCD.setError("ID Number must be numeric");
            editTextCCCD.requestFocus();
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
