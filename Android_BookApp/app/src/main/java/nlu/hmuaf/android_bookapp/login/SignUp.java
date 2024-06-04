package nlu.hmuaf.android_bookapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;

public class SignUp extends AppCompatActivity {
    private EditText signupEmail, signupPassword, signupUserName;
    private Button signupButton;
    private TextView signUpRedirectText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupUserName = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = signupUserName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                // Giả sử đây là danh sách các username đã đăng ký, thực tế bạn sẽ kiểm tra từ cơ sở dữ liệu
                List<String> existingUsernames = Arrays.asList("user1", "user2", "user3");
                if (userName.isEmpty() && email.isEmpty() && pass.isEmpty()) {
                    // Kiểm tra username không được trùng
                    if (existingUsernames.contains(userName)) {
                        Toast.makeText(SignUp.this, "Username đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Kiểm tra email có định dạng đúng và kết thúc bằng @gmail.com
                    if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                        Toast.makeText(SignUp.this, "Email phải có định dạng @gmail.com", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Kiểm tra mật khẩu có ít nhất 6 ký tự
                    if (pass.length() < 6) {
                        Toast.makeText(SignUp.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(SignUp.this, Login.class);
                    intent.putExtra("signup_success", "Đăng ký thành công");
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpRedirectText = findViewById(R.id.signup_RedirectText);
        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }
}