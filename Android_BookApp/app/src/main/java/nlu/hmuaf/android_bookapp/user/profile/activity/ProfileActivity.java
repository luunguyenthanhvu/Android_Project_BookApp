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
import android.widget.Toast;

import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.Activity.MyCart;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.home.Activity.HomeActivity;
import nlu.hmuaf.android_bookapp.user.login.Login;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private CartService cartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DarkModeUtil.applyDarkMode(this);
        cartService = new CartService(getApplicationContext());
        setContentView(R.layout.profile_activity_profile);

        Switch darkModeSwitch = findViewById(R.id.dark_mode_switch);
        boolean isDarkMode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_mode", false);
        darkModeSwitch.setChecked(isDarkMode);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModeUtil.saveDarkModeSetting(ProfileActivity.this, isChecked);
                DarkModeUtil.applyDarkMode(ProfileActivity.this);
                recreate();
            }
        });

        // Phần còn lại của mã


        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ImageView editImageView = findViewById(R.id.editImageView);
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView notificationImageView = findViewById(R.id.notification_arrow);
        notificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        ImageView reviewImageView = findViewById(R.id.reviews_arrow);
        reviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        ImageView cartImageView = findViewById(R.id.cart_arrow);
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, MyCart.class);
                startActivity(intent);
            }
        });

        ImageView privacyImageView = findViewById(R.id.privacy_arrow);
        privacyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });

        ImageView aboutImageView = findViewById(R.id.about_arrow);
        aboutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        ImageView helpImageView = findViewById(R.id.help_arrow);
        helpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa token và chuyển hướng đến màn hình đăng nhập
                clearTokenAndRedirectToLogin();
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

    private void clearTokenAndRedirectToLogin() {
        TokenResponseDTO token = MyUtils.getTokenResponse(getApplicationContext());
        // Trước đó update cart server
        cartService.syncCartWithServer(token, "Logout");
        // Xóa token từ SharedPreferences hoặc nơi lưu trữ token
        MyUtils.deleteTokenResponse(ProfileActivity.this);


        // Chuyển hướng đến màn hình đăng nhập
        Intent intent = new Intent(ProfileActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}