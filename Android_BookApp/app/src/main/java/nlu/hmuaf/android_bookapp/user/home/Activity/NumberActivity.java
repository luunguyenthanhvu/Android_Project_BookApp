package nlu.hmuaf.android_bookapp.user.home.activity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;

public class NumberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybook);

        // Ánh xạ NumberPicker từ layout
        NumberPicker numberPicker = findViewById(R.id.numberPicker);

        // Đặt giá trị tối thiểu và tối đa cho NumberPicker
        numberPicker.setMinValue(1); // Số lượng tối thiểu
        numberPicker.setMaxValue(10); // Số lượng tối đa

        // Xử lý sự kiện thay đổi giá trị trên NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // newVal là giá trị mới được chọn trên NumberPicker
                // Thực hiện các thao tác cần thiết dựa trên giá trị mới
                // Ví dụ: cập nhật TextView hiển thị giá trị mới
                TextView textView = findViewById(R.id.textViewQuantity);
                textView.setText("Số lượng: " + newVal);
            }
        });
    }
}
