package nlu.hmuaf.android_bookapp.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.home.activity.HomeActivity;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText loginMail, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText, forgotPassword;
    private ProgressBar progressBar; // Thêm ProgressBar
    private BookAppApi bookAppApi;
    private CartService cartService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cartService = new CartService(getApplicationContext());
        // Khởi tạo
        loginMail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_bar); // Khởi tạo ProgressBar

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginMail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if (checkValidate()) {
                    login(email, password);
                }
            }
        });

        // nút Quên mật khẩu
        forgotPassword = findViewById(R.id.login_forgot);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
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

    private void login(String email, String password) {
        bookAppApi = BookAppService.getClient();
        LoginRequestDTO requestDTO = LoginRequestDTO
                .builder()
                .email(email)
                .password(password)
                .build();

        // Hiển thị ProgressBar
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        Call<TokenResponseDTO> call = bookAppApi.login(requestDTO);
        call.enqueue(new Callback<TokenResponseDTO>() {
            @Override
            public void onResponse(Call<TokenResponseDTO> call, Response<TokenResponseDTO> response) {
                // Ẩn ProgressBar
                progressBar.setVisibility(View.GONE);
                loginButton.setEnabled(true);

                if (response.isSuccessful()) {
                    TokenResponseDTO responseDTO = response.body();
                    System.out.println(responseDTO);
                    if (responseDTO.getMessage().equals("Login success!")) {
                        // Lưu TokenResponseDTO vào SharedPreferences
                        MyUtils.saveTokenResponse(Login.this, responseDTO);
                        cartService.syncCartWithServer(responseDTO, "Login");
                        Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (responseDTO.getMessage().equals("Please verified your account!")) {
                        Toast.makeText(Login.this, "Vui lòng xác thực tài khoản!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Activate.class);
                        startActivity(intent);
                        finish();
                    } else if (responseDTO.getMessage().equals("WRONG PASSWORD!")) {
                        Toast.makeText(Login.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                    } else if (responseDTO.getMessage().equals("User not found!")) {
                        Toast.makeText(Login.this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponseDTO> call, Throwable t) {
                // Ẩn ProgressBar
                progressBar.setVisibility(View.GONE);
                loginButton.setEnabled(true);

                Toast.makeText(Login.this, "Đã xảy ra lỗi, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                System.out.println("Failer");
                System.out.println(t);
            }
        });
    }

    private boolean checkValidate() {
        boolean validate = true;
        String email = loginMail.getText().toString().trim();
        String pass = loginPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            showError(loginMail, "Email không hợp lệ");
            validate = false;
        } else if (pass.length() < 7) {
            showError(loginPassword, "Mật khẩu phải dài hơn 7 ký tự");
            validate = false;
        }
        return validate;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
