package nlu.hmuaf.android_bookapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        // code

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
            }
        });

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo lại activity hiện tại
                recreate();
            }
        });

    }

}
