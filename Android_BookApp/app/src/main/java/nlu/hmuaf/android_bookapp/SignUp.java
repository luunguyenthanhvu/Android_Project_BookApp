package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                if (userName.isEmpty() && email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUp.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpRedirectText = findViewById(R.id.signup_RedirectText);
        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);            }
        });
    }
}