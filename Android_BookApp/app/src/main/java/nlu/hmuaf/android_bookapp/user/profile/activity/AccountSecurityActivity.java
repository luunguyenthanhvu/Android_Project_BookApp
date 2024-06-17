package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;

public class AccountSecurityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_account_security);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account And Security");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Adding click listener for "Hồ sơ của tôi"
        findViewById(R.id.profile_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSecurityActivity.this, ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        // Adding click listener for "Tài khoản mạng xã hội"
        findViewById(R.id.social_account_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSecurityActivity.this, SocialAccountsActivity.class);
                startActivity(intent);
            }
        });

        // Adding click listener for "Đổi mật khẩu"
        findViewById(R.id.change_password_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSecurityActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        // Add other click listeners if needed
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
