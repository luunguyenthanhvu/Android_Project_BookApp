package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.activity.LogOutActivity;

public class BookActivity extends AppCompatActivity {

    private ImageView bigImageView;
    private NumberPicker numberPicker;
    private Button buttonIncrease, buttonDecrease;
    private LinearLayout contentContainer;
    private Button buttonMoTa, buttonChiTiet, buttonDanhGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        contentContainer = findViewById(R.id.contentContainer);
        buttonMoTa = findViewById(R.id.buttonMoTa);
        buttonChiTiet = findViewById(R.id.buttonChiTiet);
        buttonDanhGia = findViewById(R.id.buttonDanhGia);
        bigImageView = findViewById(R.id.bigImageView);
        numberPicker = findViewById(R.id.numberPicker);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        buttonDecrease = findViewById(R.id.buttonDecrease);

        // Thiết lập giá trị tối thiểu và tối đa cho NumberPicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        TextView tvPrevious = findViewById(R.id.tv_previous);

        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LogOutActivity
                Intent intent = new Intent(BookActivity.this, LogOutActivity.class);
                startActivity(intent);
            }
        });

        // Mặc định hiển thị nội dung của nút Mô Tả và cập nhật trạng thái nút
        updateButtonStates(buttonMoTa);
        showMoTaContent();

        // Xử lý sự kiện click cho các nút
        buttonMoTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonStates(buttonMoTa);
                showMoTaContent();
            }
        });

        buttonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonStates(buttonChiTiet);
                showChiTietContent();
            }
        });

        buttonDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonStates(buttonDanhGia);
                showDanhGiaContent();
            }
        });
    }

    private void updateButtonStates(Button selectedButton) {
        // Loại bỏ gạch chân của tất cả các button
        buttonMoTa.getPaint().setUnderlineText(false);
        buttonChiTiet.getPaint().setUnderlineText(false);
        buttonDanhGia.getPaint().setUnderlineText(false);

        // Loại bỏ kiểu chữ đậm của tất cả các button
        buttonMoTa.setTypeface(null, Typeface.NORMAL);
        buttonChiTiet.setTypeface(null, Typeface.NORMAL);
        buttonDanhGia.setTypeface(null, Typeface.NORMAL);

        // Đặt selectedButton có kiểu chữ đậm và có gạch chân
        selectedButton.setTypeface(null, Typeface.BOLD);
        selectedButton.getPaint().setUnderlineText(true);

        // Tăng khoảng cách giữa chữ và gạch chân
        selectedButton.setPadding(0, 0, 0, 8); // Điều chỉnh giá trị cuối cùng tăng hoặc giảm khoảng cách dưới chữ
    }



    private void showMoTaContent() {
        contentContainer.removeAllViews();
        TextView textView = new TextView(this);
        textView.setText(R.string.NMTDQ);
        contentContainer.addView(textView);
    }

    private void showChiTietContent() {
        contentContainer.removeAllViews();
        TextView textView = new TextView(this);
        textView.setText("Loại sản phẩm : Bìa mềm\n" +
                "Kích thước : 14 x 20.5 cm\n" +
                "Số trang : 200\n" +
                "Tác giả : Alexander\n" +
                "Dịch giả :  Trang\n" +
                "Nhà xuất bản : NXB Thế giới\n");
        contentContainer.addView(textView);

    }

    private void showDanhGiaContent() {
        contentContainer.removeAllViews();

        // Hiển thị đánh giá của khách hàng trước
        TextView customerReview1 = new TextView(this);
        customerReview1.setText("Tên: Nguyễn Văn A\nĐánh giá: Sản phẩm rất tốt!");
        contentContainer.addView(customerReview1);

        // Thêm nút "Thêm đánh giá"
        Button buttonAddReview = new Button(this);
        buttonAddReview.setText("Thêm đánh giá");
        contentContainer.addView(buttonAddReview);

        // Set OnClickListener for the "Thêm đánh giá" button
        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị form để nhập đánh giá mới
                showAddReviewForm();
            }
        });
    }

    private void showAddReviewForm() {
        // Inflate layout from XML
        View addReviewForm = getLayoutInflater().inflate(R.layout.review_layout, contentContainer, false);
        contentContainer.addView(addReviewForm, 0); // Hiển thị form trên đầu tiên

        // Get references to EditText and Button in the "Thêm đánh giá" form
        EditText editTextName = addReviewForm.findViewById(R.id.editTextName);
        EditText editTextReview = addReviewForm.findViewById(R.id.editTextReview);
        Button buttonSubmitReview = addReviewForm.findViewById(R.id.buttonSubmitReview);

        // Set OnClickListener for the "Gửi đánh giá" button in the form
        buttonSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields
                String userName = editTextName.getText().toString();
                String userReview = editTextReview.getText().toString();

                // Process the user input (e.g., save it to a database, display it on the screen, etc.)
                // Here, you can implement your logic to handle the user review data

                // Example: Display the user's review as a TextView
                TextView reviewTextView = new TextView(BookActivity.this);
                reviewTextView.setText("Tên: " + userName + "\nĐánh giá: " + userReview);
                contentContainer.removeViewAt(0); // Xóa form thêm đánh giá
                contentContainer.addView(reviewTextView, 0); // Hiển thị đánh giá mới lên đầu tiên
            }
        });
    }


    public void onSmallImageClick(View view) {
        // Lấy tag của ImageView đã nhấn
        String tag = view.getTag().toString();

        // Tùy thuộc vào tag, hiển thị ảnh tương ứng trong khung lớn
        switch (tag) {
            case "book":
                bigImageView.setImageResource(R.drawable.book_login);
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
