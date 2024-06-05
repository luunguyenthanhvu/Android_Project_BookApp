package nlu.hmuaf.android_bookapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import lombok.experimental.var;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText loginMail, loginPassword;
    private Button loginButton;
<<<<<<< HEAD
    private TextView loginForgot, signupRedirectText;
=======
    private TextView signupRedirectText;
    private BookAppApi bookAppApi;
>>>>>>> origin/main

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
<<<<<<< HEAD
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng điền vào ô trống!", Toast.LENGTH_SHORT).show();
                }
                if (email.equals("Admin") && password.equals("123")) {
                    Intent intent = new Intent(Login.this, Activate.class);
                    startActivity(intent);
                    finish(); // đóng activity
=======

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
>>>>>>> origin/main
                } else {
                    login(email, password, savedInstanceState);
//                    if (email.equals("Admin") && password.equals("123")) {
//                        Intent intent = new Intent(Login.this, Activate.class);
//                        startActivity(intent);
//                        finish(); // đóng activity
//                    } else {
//                        Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
//                    }
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

    private TokenResponseDTO login(String email, String password, Bundle savedInstanceState) {
        bookAppApi = BookAppService.getClient();
        LoginRequestDTO requestDTO = LoginRequestDTO
                .builder()
                .email(email)
                .password(password)
                .build();

        Call<TokenResponseDTO> call = bookAppApi.login(requestDTO);
        call.enqueue(new Callback<TokenResponseDTO>() {
            @Override
            public void onResponse(Call<TokenResponseDTO> call, Response<TokenResponseDTO> response) {
                TokenResponseDTO responseDTO = response.body();
                System.out.println(responseDTO);
                if (responseDTO.getMessage().equals("Login success!")) {
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Activate.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TokenResponseDTO> call, Throwable t) {
                System.out.println("Failer");
                System.out.println(t);
            }
        });
        return null;
    }
}