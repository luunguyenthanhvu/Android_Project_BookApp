package nlu.hmuaf.android_bookapp.user.home.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    private ImageView bigImageView;
    private NumberPicker numberPicker;
    private Button buttonIncrease, buttonDecrease;
    private LinearLayout contentContainer;
    private Button buttonMoTa, buttonChiTiet, buttonDanhGia;
    private int quantityBookInCart = 0;
    private long bookId;
    private BookAppApi bookAppApi;
    private ProgressBar progressBar;
    private TextView title, author;
    private ImageView imgBook;
    private ImageView imgCopyB2;
    private TextView nameB, priceB2, tvDiscountB2, originalPriceB2, star, tonKhoQuantity;
    private BookDetailResponseDTO bookDetailResponseDTO = new BookDetailResponseDTO();
    private LinearLayout smallImagesContainer;
    private CartService cartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);
        // get the cart service
        cartService = new CartService(getApplicationContext());

        // đăng ký sự kiện observe để render lại sự thay đổi của giỏ hàng
        cartService.getCartSizeLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cartSize) {
                updateCartQuantity(cartSize);
            }
        });
        // Lấy ID sách từ Intent
        Intent intent = getIntent();
        bookId = intent.getLongExtra("BOOK_ID", -1);
        getBookByBookId(bookId);
//        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.tv_title);
        priceB2 = findViewById(R.id.tv_priceB2);
        tvDiscountB2 = findViewById(R.id.tv_discountB2);
        originalPriceB2 = findViewById(R.id.originalPriceB2);
        star = findViewById(R.id.star);
        tonKhoQuantity = findViewById(R.id.tonKhoQuantity);
        contentContainer = findViewById(R.id.contentContainer);
        buttonMoTa = findViewById(R.id.buttonMoTa);
        buttonChiTiet = findViewById(R.id.buttonChiTiet);
        buttonDanhGia = findViewById(R.id.buttonDanhGia);
        bigImageView = findViewById(R.id.bigImageView);
        numberPicker = findViewById(R.id.numberPicker);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        buttonDecrease = findViewById(R.id.buttonDecrease);
        smallImagesContainer = findViewById(R.id.smallImagesContainer);
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
        LinearLayout wishlistLayout = findViewById(R.id.libraryLayout);
        wishlistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

        // Mặc định hiển thị nội dung của nút Mô Tả và cập nhật trạng thái nút
        updateButtonStates(buttonMoTa);

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
        textView.setText(bookDetailResponseDTO.getDescription());
        contentContainer.addView(textView);
    }

    private void showChiTietContent() {
        contentContainer.removeAllViews();
        TextView textView = new TextView(this);
        textView.setText(bookDetailResponseDTO.getDetails() +
                "Nhà xuất bản :" + bookDetailResponseDTO.getPublicCompany() + "\n");
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
        buttonAddReview.setBackgroundResource(R.drawable.rounded_button); // Ví dụ: màu cam
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


    public void onSmallImageClick(String url) {
        Picasso.get().load(url).into(bigImageView);
    }

    private void loadBookData() {
        if (bookDetailResponseDTO != null) {
            title.setText(bookDetailResponseDTO.getTitle());
            double averageRating = bookDetailResponseDTO.getAverageRating();
            double roundedRating = Math.round(averageRating * 10.0) / 10.0;
            star.setText("Đánh giá: " + String.format("%.1f", roundedRating));

            tonKhoQuantity.setText(String.valueOf(bookDetailResponseDTO.getAvailableQuantity()));
            // Thiết lập giá trị tối thiểu và tối đa cho NumberPicker
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(bookDetailResponseDTO.getAvailableQuantity());
            String[] imgList = bookDetailResponseDTO.getImg();
            Picasso.get().load(imgList[0]).into(bigImageView);
            for (String url : imgList) {
                createSmallImageView(url);
            }
            if (bookDetailResponseDTO.getDiscount() != 0.0) {
                priceB2.setText(MyUtils.convertToVND(bookDetailResponseDTO.getDiscountedPrice()));
                tvDiscountB2.setText(bookDetailResponseDTO.getDiscount() + "%");
                originalPriceB2.setText(String.valueOf(bookDetailResponseDTO.getOriginalPrice()));
                tvDiscountB2.setVisibility(View.VISIBLE);

                // setting originalPrice
                originalPriceB2.setText(MyUtils.convertToVND(bookDetailResponseDTO.getOriginalPrice()));
                originalPriceB2.setVisibility(View.VISIBLE);
                originalPriceB2.setPaintFlags(originalPriceB2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                priceB2.setText(MyUtils.convertToVND(bookDetailResponseDTO.getOriginalPrice()));
                tvDiscountB2.setVisibility(View.GONE);
                originalPriceB2.setVisibility(View.GONE);
            }
            showMoTaContent();
        }
    }


    private void getBookByBookId(long bookId) {
        //progressBar.setVisibility(View.VISIBLE);
        bookAppApi = BookAppService.getClient();
        Call<BookDetailResponseDTO> call = bookAppApi.getBookDetailByBookId(bookId);
        call.enqueue(new Callback<BookDetailResponseDTO>() {
            @Override
            public void onResponse(Call<BookDetailResponseDTO> call, Response<BookDetailResponseDTO> response) {
//                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    bookDetailResponseDTO = response.body();
                    loadBookData();
                }
            }

            @Override
            public void onFailure(Call<BookDetailResponseDTO> call, Throwable throwable) {

            }
        });
    }

    private void createSmallImageView(String imageUrl) {
        // Tạo mới ImageView
        ImageView imageView = new ImageView(this);

        // Thiết lập id
        imageView.setId(View.generateViewId());

        // Thiết lập các thuộc tính cho ImageView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.small_image_width), // Độ rộng
                getResources().getDimensionPixelSize(R.dimen.small_image_height)); // Độ cao
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setTag(imageUrl);

        // Load ảnh từ URL sử dụng Picasso
        Picasso.get().load(imageUrl).into(imageView);

        // Đăng ký sự kiện onClick cho ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSmallImageClick(imageUrl);
            }
        });

        // Thêm ImageView vào LinearLayout chứa ảnh nhỏ
        smallImagesContainer.addView(imageView);
    }

    private void updateCartQuantity(int cartSize) {
        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
        quantityTextView.setText(String.valueOf(cartSize));
        quantityTextView.setVisibility(View.VISIBLE);
    }
}
