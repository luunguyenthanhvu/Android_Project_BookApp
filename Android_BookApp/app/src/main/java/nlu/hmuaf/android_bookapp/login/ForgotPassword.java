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

public class ForgotPassword extends AppCompatActivity {
    private EditText email_edit_text;
    private Button send_button;
    private TextView cancel, reset;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forgot);

        // Khởi tạo
        email_edit_text = findViewById(R.id.email_edit_text);
        send_button = findViewById(R.id.send_button);

        // bấm nút "Lấy lại mật khẩu"
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidate()) {
                    Toast.makeText(ForgotPassword.this, "Vui lòng kiểm tra email của bạn!", Toast.LENGTH_SHORT).show();
                    // code thêm ở đây
                }
            }
        });

//        cancel = findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ForgotPassword.this, Login.class);
//                startActivity(intent);
//            }
//        });
//
//        reset = findViewById(R.id.reset);
//        reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                email_edit_text.setText("");
//            }
//        });

    }

    private boolean checkValidate() {
        boolean validate = true;
        String email = email_edit_text.getText().toString().trim();

        if(email.isEmpty() ) {
            showError(email_edit_text,  "Vui lòng nhập email!");
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
