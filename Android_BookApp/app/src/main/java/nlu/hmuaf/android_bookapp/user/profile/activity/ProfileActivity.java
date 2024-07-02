package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;

import com.squareup.picasso.Picasso;

import nlu.hmuaf.android_bookapp.animation.add_to_cart.CircleImageView;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.bill.activity.MyBill;
import nlu.hmuaf.android_bookapp.user.cart_user.activity.MyCart;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.login.Login;
import nlu.hmuaf.android_bookapp.user.order.activity.OrderList;
import nlu.hmuaf.android_bookapp.user.profile.classess.UserDetails;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_EDIT_PROFILE = 1;

    private CartService cartService;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private ImageView circleImageView;
    private TokenResponseDTO tokenResponseDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tokenResponseDTO = MyUtils.getTokenResponse(getApplicationContext());
        DarkModeUtil.applyDarkMode(this);
        cartService = new CartService(getApplicationContext());
        setContentView(R.layout.profile_activity_profile);

        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);
        circleImageView = findViewById(R.id.profile_image);

        userEmailTextView.setText(tokenResponseDTO.getEmail());
        userNameTextView.setText(tokenResponseDTO.getUsername());
        Picasso.get().load(tokenResponseDTO.getImg()).into(circleImageView);

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
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
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
                // Chuyển hướng sang Activity ReviewActivity
                Intent intent = new Intent(ProfileActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        ImageView cartImageView = findViewById(R.id.cart_arrow);
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity MyCart
                Intent intent = new Intent(ProfileActivity.this, MyCart.class);
                startActivity(intent);
            }
        });

        ImageView privacyImageView = findViewById(R.id.privacy_arrow);
        privacyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity PrivacyActivity
                Intent intent = new Intent(ProfileActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });

        ImageView aboutImageView = findViewById(R.id.about_arrow);
        aboutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity AboutActivity
                Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        ImageView helpImageView = findViewById(R.id.help_arrow);
        helpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity HelpActivity
                Intent intent = new Intent(ProfileActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        ImageView mybillImageView = findViewById(R.id.mybill_arrow);
        mybillImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, OrderList.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTokenAndRedirectToLogin();
            }
        });

        ImageView mybillImageArrow = findViewById(R.id.mybill_arrow);
        mybillImageArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, OrderList.class);
                startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK && data != null) {
            UserDetails updatedUser = (UserDetails) data.getSerializableExtra("updated_user");
            if (updatedUser != null) {
                userNameTextView.setText(String.format("%s %s", updatedUser.getFirstName(), updatedUser.getLastName()));
                userEmailTextView.setText(updatedUser.getEmail());
            }
        }
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
