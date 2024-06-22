package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.login.Login;
import nlu.hmuaf.android_bookapp.user.login.SignUp;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;

public class LogOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DarkModeUtil.applyDarkMode(this);


        setContentView(R.layout.profile_activity_logout);

        Switch darkModeSwitch = findViewById(R.id.dark_mode_switch);
        boolean isDarkMode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_mode", false);
        darkModeSwitch.setChecked(isDarkMode);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModeUtil.saveDarkModeSetting(LogOutActivity.this, isChecked);
                DarkModeUtil.applyDarkMode(LogOutActivity.this);
                recreate();
            }
        });

        // Phần còn lại của mã
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());



        ImageView privacyImageView = findViewById(R.id.privacy_arrow);
        privacyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(LogOutActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });

        ImageView aboutImageView = findViewById(R.id.about_arrow);
        aboutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(LogOutActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        ImageView helpImageView = findViewById(R.id.help_arrow);
        helpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(LogOutActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton  = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogOutActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        Button registerButton  = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogOutActivity.this, SignUp.class);
                startActivity(intent);
                finish();
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