package nlu.hmuaf.android_bookapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;

public class Login extends AppCompatActivity {
    private EditText loginMail, loginPassword;
    private Button loginButton;
    private TextView loginForgot, signupRedirectText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo
        loginMail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginMail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng điền vào ô trống!", Toast.LENGTH_SHORT).show();
                }
                if (email.equals("Admin") && password.equals("123")) {
                    Intent intent = new Intent(Login.this, Activate.class);
                    startActivity(intent);
                    finish(); // đóng activity
                } else {
                    Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // nút Quên mật khẩu?
        loginForgot = findViewById(R.id.login_forgot);
        loginForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        // nút Đăng ký
        signupRedirectText = findViewById(R.id.signupRedirectText);
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        // Nhận dữ liệu từ Intent từ trang Đăng ký
        Intent intent = getIntent();
        String message = intent.getStringExtra("signup_success");

        // Nếu có thông báo đăng ký thành công, hiển thị Toast
        if (message != null && !message.isEmpty()) {
            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
        }
    }

}