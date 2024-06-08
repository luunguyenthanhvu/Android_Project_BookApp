package nlu.hmuaf.android_bookapp.login;

import android.annotation.SuppressLint;
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
    private EditText signupEmail, signupPassword, signupUserName, signupRetypePassword;
    private Button signupButton;
    private TextView loginRedirectText;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupUserName = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupRetypePassword = findViewById(R.id.signup_retype_password);
        signupButton = findViewById(R.id.signup_button);

        // Bấm nút đăng ký
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (checkValidate()) {
                   Intent intent = new Intent(SignUp.this, Activate.class);
                   startActivity(intent);
               }
            }
        });

        loginRedirectText = findViewById(R.id.login_RedirectText);
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkValidate() {
        boolean validate = true;
        String userName = signupUserName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String pass = signupPassword.getText().toString().trim();
        String retype_pass = signupRetypePassword.getText().toString().trim();

        if(userName.isEmpty() || email.isEmpty() || pass.isEmpty() || retype_pass.isEmpty()) {
            Toast.makeText(this,  "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if(userName.length()<6) {
            showError(signupUserName, "Tên tài khoản không hợp lệ");
            validate = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            showError(signupEmail, "Email không hợp lệ");
            validate = false;
        } else if (pass.length()<7) {
            showError(signupPassword, "Mật khẩu phải dài hơn 7 ký tự");
            validate = false;
        } else if (!retype_pass.equals(pass)) {
            showError(signupPassword, "Hai mật khẩu không giống nhau");
            validate = false;
        }
        return validate;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}