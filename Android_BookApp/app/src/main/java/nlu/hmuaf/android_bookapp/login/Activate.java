package nlu.hmuaf.android_bookapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.CartUser.Activity.MainActivity;
import nlu.hmuaf.android_bookapp.HomeScreen.Activity.HomeActivity;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.activity.AboutActivity;

public class Activate extends AppCompatActivity {
    private EditText activateNumber;
    private Button button_login;
    private TextView sendCode, cancel, reset;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated);

        // Khởi tạo
        activateNumber = findViewById(R.id.activate_edit_text);
        button_login = findViewById(R.id.activate_login);
        sendCode = findViewById(R.id.sendCode);

        // Mã xác nhận chỉ được nhập số
        activateNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        // kích hoạt nút bấm gửi mã xác nhận
//        sendCode.setOnClickListener
        // code


        // bấm nút Đăng nhập
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = activateNumber.getText().toString().trim();
                // nếu mã xác nhận đúng
                if (number.equals("")) {
                    Intent intent = new Intent(Activate.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Tùy chọn: đóng activity để không quay lại khi nhấn quay lại
                } else {
                    Toast.makeText(Activate.this, "Mã xác nhận sai! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activate.this, Login.class);
                startActivity(intent);
            }
        });

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateNumber.setText("");
            }
        });

    }

    private boolean checkValidate() {
        boolean validate = true;
        String number = activateNumber.getText().toString().trim();

        if(number.isEmpty() ) {
            showError(activateNumber,  "Vui lòng bấm nút Gửi!");
            validate = false;
        } else if(!number.matches("[0-9]")) {
            showError(activateNumber,  "Chỉ được nhập số!");
            validate = false;
        }
        return validate;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}