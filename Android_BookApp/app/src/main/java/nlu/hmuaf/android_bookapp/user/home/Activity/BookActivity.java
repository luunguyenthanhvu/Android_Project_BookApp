package nlu.hmuaf.android_bookapp.user.home.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.animation.add_to_cart.CircleAnimation;
import nlu.hmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.activity.MyCart;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    BookActivity extends AppCompatActivity {

    private ImageView bigImageView, bigImageViewCopy;
    private LinearLayout priceDcLayout;
    private NumberPicker numberPicker;
    private Button buttonIncrease, buttonDecrease;
    private LinearLayout contentContainer;
    private Button buttonMoTa, buttonChiTiet, buttonDanhGia;
    private int quantityBookInCart = 0;
    private long bookId;
    private BookAppApi bookAppApi;
    private ProgressBar progressBar;
    private TextView title, author;
    private ImageView imgBook, addProduct;
    private ImageView imgCopyB2;
    private TextView nameB, priceB2, tvDiscountB2, originalPriceB2, star, tonKhoQuantity;
    private BookDetailResponseDTO bookDetailResponseDTO = new BookDetailResponseDTO();
    private LinearLayout smallImagesContainer;
    private CartService cartService;
    private TextView cartBadgeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);
        cartBadgeTextView = findViewById(R.id.cart_badge_text_view);
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
        priceDcLayout = findViewById(R.id.priceDcLayout);
        tonKhoQuantity = findViewById(R.id.tonKhoQuantity);
        contentContainer = findViewById(R.id.contentContainer);
        buttonMoTa = findViewById(R.id.buttonMoTa);
        buttonChiTiet = findViewById(R.id.buttonChiTiet);
        buttonDanhGia = findViewById(R.id.buttonDanhGia);
        addProduct = findViewById(R.id.add_product);
        bigImageView = findViewById(R.id.bigImageView);
        bigImageViewCopy = findViewById(R.id.bigImageViewCopy);
        numberPicker = findViewById(R.id.numberPicker);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        buttonDecrease = findViewById(R.id.buttonDecrease);
        buttonDecrease.setText("-");
        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        cartActionInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isUserLoggedIn(BookActivity.this)) {
                    Intent intent = new Intent(BookActivity.this, MyCart.class);
                    startActivity(intent);
                }
            }
        });
        smallImagesContainer = findViewById(R.id.smallImagesContainer);
        ImageView tvPrevious = findViewById(R.id.tv_previous);

        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isUserLoggedIn(BookActivity.this)) {
                    ListBookResponseDTO dto =
                            ListBookResponseDTO.builder()
                                    .bookId(bookDetailResponseDTO.getBookId())
                                    .title(bookDetailResponseDTO.getTitle())
                                    .bookId(bookDetailResponseDTO.getBookId())
                                    .thumbnail(bookDetailResponseDTO.getImg()[0])
                                    .author(bookDetailResponseDTO.getAuthor())
                                    .averageRating(bookDetailResponseDTO.getAverageRating())
                                    .discount(bookDetailResponseDTO.getDiscount())
                                    .quantity((long) bookDetailResponseDTO.getAvailableQuantity())
                                    .originalPrice(bookDetailResponseDTO.getOriginalPrice())
                                    .discountedPrice(bookDetailResponseDTO.getDiscountedPrice())
                                    .build();
                    cartService.updateProductCart(MyUtils.getTokenResponse(BookActivity.this), dto,
                            numberPicker.getValue());
                    // add animation
                    ImageView imageView = findViewById(R.id.bigImageViewCopy);
                    if (imageView != null) {
                        makeFlyAnimation(imageView);
                    }
                }
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
        // get cart quantity
        updateCartQuantity();
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
        textView.setPadding(0, 0, 0, 20);
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
        Picasso.get().load(url).fit()
                .centerInside().into(bigImageView);
        Picasso.get().load(url).fit()
                .centerInside().into(bigImageViewCopy);
    }

    private void loadBookData() {
        if (bookDetailResponseDTO != null) {
            title.setText(bookDetailResponseDTO.getTitle());
            double averageRating = bookDetailResponseDTO.getAverageRating();
            double roundedRating = Math.round(averageRating * 10.0) / 10.0;
            star.setText("Đánh giá: " + String.format("%.1f", roundedRating));

            tonKhoQuantity.setText(String.valueOf(bookDetailResponseDTO.getAvailableQuantity() + " sản phẩm."));
            // Thiết lập giá trị tối thiểu và tối đa cho NumberPicker
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(bookDetailResponseDTO.getAvailableQuantity());
            String[] imgList = bookDetailResponseDTO.getImg();
            Picasso.get().load(imgList[0]).fit()
                    .centerInside().into(bigImageView);
            Picasso.get().load(imgList[0]).fit()
                    .centerInside().into(bigImageViewCopy);
            for (String url : imgList) {
                createSmallImageView(url);
            }
            if (bookDetailResponseDTO.getDiscount() != 0.0) {
                priceB2.setText(MyUtils.convertToVND(bookDetailResponseDTO.getDiscountedPrice()));
                int discountPercentage = (int) (bookDetailResponseDTO.getDiscount() * 100);
                String discountText = String.format("%d%%", discountPercentage);
                tvDiscountB2.setText(discountText);

                originalPriceB2.setText(String.valueOf(bookDetailResponseDTO.getOriginalPrice()));
                tvDiscountB2.setVisibility(View.VISIBLE);
                priceDcLayout.setVisibility(View.VISIBLE);
                // setting originalPrice
                originalPriceB2.setText(MyUtils.convertToVND(bookDetailResponseDTO.getOriginalPrice()));
                originalPriceB2.setVisibility(View.VISIBLE);
                originalPriceB2.setPaintFlags(originalPriceB2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                priceB2.setText(MyUtils.convertToVND(bookDetailResponseDTO.getOriginalPrice()));
                tvDiscountB2.setVisibility(View.GONE);
                originalPriceB2.setVisibility(View.GONE);
                priceDcLayout.setVisibility(View.GONE);
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
        Picasso.get().load(imageUrl).fit()
                .centerInside().into(imageView);

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

    private void updateCartQuantity() {
        if (MyUtils.getTokenResponse(this) != null) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Lấy số lượng sản phẩm trong giỏ hàng từ cơ sở dữ liệu
                int quantityBookInCart = cartService.getUserCart(MyUtils.getTokenResponse(getApplicationContext()).getUserId()).size();

                // Cập nhật giao diện người dùng
                runOnUiThread(() -> {
                    // Tìm và cập nhật TextView hiển thị số lượng sản phẩm trong giỏ hàng
                    FrameLayout cartActionInclude = findViewById(R.id.cartItem);
                    TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
                    quantityTextView.setText(String.valueOf(quantityBookInCart));
                    quantityTextView.setVisibility(View.VISIBLE);
                });
            });
        } else {
            FrameLayout cartActionInclude = findViewById(R.id.cartItem);
            TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
            quantityTextView.setText(String.valueOf(0));
            quantityTextView.setVisibility(View.VISIBLE);
        }
    }

    private void makeFlyAnimation(ImageView targetView) {
        FrameLayout destView = findViewById(R.id.cartItem);

        CircleAnimation animation = new CircleAnimation();
        animation.attachActivity(this).setTargetView(targetView).setMoveDuration(300).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Optional: Perform actions when animation starts
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(
                        BookActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Optional: Handle animation cancellation
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Optional: Handle animation repeat
            }
        }).startAnimation();
    }

}
