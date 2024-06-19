package nlu.hmuaf.android_bookapp.user.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.ProgressBar;

public class SignUp extends AppCompatActivity {
    private EditText signupEmail, signupPassword, signupUserName, signupRetypePassword;
    private Button signupButton;
    private TextView loginRedirectText;
    private ProgressBar progressBar; // Thêm ProgressBar
    private BookAppApi bookAppApi;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupUserName = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupRetypePassword = findViewById(R.id.signup_retype_password);
        signupButton = findViewById(R.id.signup_button);
        progressBar = findViewById(R.id.progress_bar); // Khởi tạo ProgressBar

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signupUserName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String retype_pass = signupRetypePassword.getText().toString().trim();
                if (checkValidate()) {
                    sendSignUpRequest(username, email, pass);
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

    public void sendSignUpRequest(String username, String email, String pass) {
        bookAppApi = BookAppService.getClient();
        RegisterRequestDTO requestDTO = RegisterRequestDTO
                .builder()
                .username(username)
                .email(email)
                .password(pass)
                .build();

        // Hiển thị ProgressBar
        progressBar.setVisibility(View.VISIBLE);
        signupButton.setEnabled(false);

        Call<MessageResponseDTO> call = bookAppApi.register(requestDTO);
        call.enqueue(new Callback<MessageResponseDTO>() {
            @Override
            public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                // Ẩn ProgressBar
                progressBar.setVisibility(View.GONE);
                signupButton.setEnabled(true);

                if (response.isSuccessful()) {
                    MessageResponseDTO responseDTO = response.body();
                    if (responseDTO.getMessage().equals("Register success!")) {
                        Toast.makeText(SignUp.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, Activate.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else if (responseDTO.getMessage().equals("Username used!")) {
                        Toast.makeText(SignUp.this, "Tên người dùng đã được sử dụng!", Toast.LENGTH_SHORT).show();
                    } else if (responseDTO.getMessage().equals("User already exist!")) {
                        Toast.makeText(SignUp.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Lỗi sever!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                // Ẩn ProgressBar
                progressBar.setVisibility(View.GONE);
                signupButton.setEnabled(true);
                Toast.makeText(SignUp.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                System.out.println(throwable);
            }
        });
    }

    private boolean checkValidate() {
        boolean validate = true;
        String userName = signupUserName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String pass = signupPassword.getText().toString().trim();
        String retype_pass = signupRetypePassword.getText().toString().trim();

        if (userName.isEmpty() || email.isEmpty() || pass.isEmpty() || retype_pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            validate = false;
        } else if (userName.length() < 6) {
            showError(signupUserName, "Tên tài khoản không hợp lệ");
            validate = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            showError(signupEmail, "Email không hợp lệ");
            validate = false;
        } else if (pass.length() < 7) {
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
