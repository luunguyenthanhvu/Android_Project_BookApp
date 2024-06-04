package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;


public class BookActivity extends AppCompatActivity {

    private ImageView bigImageView;
    private NumberPicker numberPicker;
    private Button buttonIncrease, buttonDecrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        bigImageView = findViewById(R.id.bigImageView);
        numberPicker = findViewById(R.id.numberPicker);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        buttonDecrease = findViewById(R.id.buttonDecrease);

        // Thiết lập giá trị tối thiểu và tối đa cho NumberPicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        // Sự kiện nhấn vào nút cộng
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = numberPicker.getValue();
                if (value < numberPicker.getMaxValue()) {
                    numberPicker.setValue(value + 1);
                }
            }
        });

        // Sự kiện nhấn vào nút trừ
        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = numberPicker.getValue();
                if (value > numberPicker.getMinValue()) {
                    numberPicker.setValue(value - 1);
                }
            }
        });

        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến SearchActivity
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến ProfileActivity
//        LinearLayout profileLayout = findViewById(R.id.profileLayout);
//        profileLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LibraryActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}

    }
        public void onSmallImageClick (View view){
            // Lấy tag của ImageView đã nhấn
            String tag = view.getTag().toString();

            // Tùy thuộc vào tag, hiển thị ảnh tương ứng trong khung lớn
            switch (tag) {
                case "book":
                    bigImageView.setImageResource(R.drawable.book);
                    break;
                case "bell":
                    bigImageView.setImageResource(R.drawable.bell);
                    break;
                case "profile":
                    bigImageView.setImageResource(R.drawable.profile);
                    break;
            }
        }
    }

