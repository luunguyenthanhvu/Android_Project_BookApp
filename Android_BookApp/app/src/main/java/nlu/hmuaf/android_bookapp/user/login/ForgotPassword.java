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

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.request.ForgotPasswordDTO;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    private EditText email_edit_text;
    private Button send_button;
    private TextView cancel, reset;
    private BookAppApi bookAppApi;
    private ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forgot);

        // Khởi tạo
        email_edit_text = findViewById(R.id.email_edit_text);
        send_button = findViewById(R.id.send_button);
        progressBar = findViewById(R.id.progress_bar);

        // bấm nút "Lấy lại mật khẩu"
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidate()) {
                    String email = email_edit_text.getText().toString().trim();
                    sendNewPass(email);
                }
            }
        });
    }

    private void sendNewPass(String email) {
        // Hiển thị ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        bookAppApi = BookAppService.getClient();
        ForgotPasswordDTO requestDTO = ForgotPasswordDTO
                .builder()
                .email(email)
                .build();

        Call<MessageResponseDTO> call = bookAppApi.forgotPassword(requestDTO);
        call.enqueue(new Callback<MessageResponseDTO>() {
            @Override
            public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                // Ẩn ProgressBar
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    MessageResponseDTO messageResponseDTO = response.body();
                    if (messageResponseDTO.getMessage().equals("Update Pass Success")) {
                        Toast.makeText(ForgotPassword.this, "Vui lòng kiểm tra trong email!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ForgotPassword.this, "Vui lòng kiểm tra trong email!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgotPassword.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                System.out.println(throwable);
            }
        });
    }

    private boolean checkValidate() {
        boolean validate = true;
        String email = email_edit_text.getText().toString().trim();

        if (email.isEmpty()) {
            showError(email_edit_text, "Vui lòng nhập email!");
            validate = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            showError(email_edit_text, "Email không hợp lệ");
            validate = false;
        }
        return validate;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
