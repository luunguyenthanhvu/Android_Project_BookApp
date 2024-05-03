package nlu.hmuaf.android_bookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Switch darkModeSwitch = findViewById(R.id.dark_mode_switch);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Enable Dark Mode
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // Disable Dark Mode
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        Button editButton = findViewById(R.id.edit_button);
        TextView userNameTextView = findViewById(R.id.user_name);
        TextView userEmailTextView = findViewById(R.id.user_email);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện click vào nút sửa thông tin ở đây
                // Ví dụ: mở hộp thoại sửa thông tin hoặc chuyển đến một Activity khác

                // Tạo AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Nhập thông tin mới");

                // Giao diện người dùng để nhập username và email mới
                final EditText usernameEditText = new EditText(ProfileActivity.this);
                final EditText emailEditText = new EditText(ProfileActivity.this);

                // Đặt giá trị ban đầu cho username và email
                usernameEditText.setText(userNameTextView.getText().toString());
                emailEditText.setText(userEmailTextView.getText().toString());

                // Thêm EditText vào AlertDialog
                LinearLayout layout = new LinearLayout(ProfileActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(usernameEditText);
                layout.addView(emailEditText);
                builder.setView(layout);

                // Thiết lập nút OK
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy nội dung đã nhập từ EditText
                        String newUsername = usernameEditText.getText().toString();
                        String newEmail = emailEditText.getText().toString();

                        // Đặt nội dung mới cho TextView "Username" và "Email"
                        userNameTextView.setText(newUsername);
                        userEmailTextView.setText(newEmail);
                    }
                });

                // Thiết lập nút Cancel
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Hiển thị hộp thoại
                builder.show();
            }
        });


        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TextView notificationTextView = findViewById(R.id.notification_label);
        notificationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        TextView reviewTextView = findViewById(R.id.my_reviews_label);
        reviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        TextView cartTextView = findViewById(R.id.my_cart_label);
        cartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        TextView privacyTextView = findViewById(R.id.privacy_policy_label);
        privacyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });

        TextView aboutTextView = findViewById(R.id.about_label);
        aboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        TextView helpTextView = findViewById(R.id.help_label);
        helpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ProfileActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });


    }
}