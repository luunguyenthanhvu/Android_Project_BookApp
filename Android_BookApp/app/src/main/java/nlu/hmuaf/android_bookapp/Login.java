package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText loginMail, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;

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
                if (email.equals("Admin@123") && password.equals("123")) {
                    Toast.makeText(Login.this, "Login Ok", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Tùy chọn: đóng Login activity để không quay lại khi nhấn back
                } else {
                    Toast.makeText(Login.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupRedirectText = findViewById(R.id.signupRedirectText);
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });


    }



}