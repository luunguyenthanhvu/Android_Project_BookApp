package nlu.hmuaf.android_bookapp.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activate extends AppCompatActivity {
    private EditText activateNumber;
    private Button button_login;
    private BookAppApi bookAppApi;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated);

        // Khởi tạo
        activateNumber = findViewById(R.id.activate_edit_text);
        button_login = findViewById(R.id.activate_login);
        progressBar = findViewById(R.id.progress_bar);

        // Mã xác nhận chỉ được nhập số
        activateNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Nhận dữ liệu email từ Intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        // Bấm nút Đăng nhập
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = activateNumber.getText().toString().trim();
                verifyAccount(email, otp);
            }
        });
    }

    private void verifyAccount(String email, String otp) {
        // Hiển thị ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        bookAppApi = BookAppService.getClient();
        VerifyRequestDTO requestDTO = VerifyRequestDTO.builder().email(email).otp(otp).build();

        Call<MessageResponseDTO> call = bookAppApi.verifyAccount(requestDTO);
        call.enqueue(new Callback<MessageResponseDTO>() {
            @Override
            public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                // Ẩn ProgressBar
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    MessageResponseDTO messageResponseDTO = response.body();
                    if (messageResponseDTO.getMessage().equals("Verified success")) {
                        Toast.makeText(Activate.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activate.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else if (messageResponseDTO.getMessage().equals("User already verified!")) {
                        Toast.makeText(Activate.this, "Tài khoản đã được xác thực!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activate.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else if (messageResponseDTO.getMessage().equals("Your otp is expired! Please validate again")) {
                        Toast.makeText(Activate.this, "Mã otp đã hết hạn. Vui lòng nhập mã otp mới!", Toast.LENGTH_SHORT).show();
                    } else if (messageResponseDTO.getMessage().equals("Please enter right OTP")) {
                        Toast.makeText(Activate.this, "Vui lòng nhập đúng mã otp!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activate.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activate.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Activate.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                System.out.println(throwable);
            }
        });
    }

    private boolean checkValidate() {
        boolean validate = true;
        String number = activateNumber.getText().toString().trim();

        if (number.isEmpty()) {
            showError(activateNumber, "Vui lòng bấm nút Gửi!");
            validate = false;
        } else if (!number.matches("[0-9]")) {
            showError(activateNumber, "Chỉ được nhập số!");
            validate = false;
        }
        return validate;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}