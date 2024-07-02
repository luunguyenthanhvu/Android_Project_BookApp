package nlu.hmuaf.android_bookapp.user.home.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.animation.add_to_cart.CircleAnimation;
import nlu.hmuaf.android_bookapp.api_google_map.GoogleMapActivity;
import nlu.hmuaf.android_bookapp.dto.response.APIKeyResponse;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.activity.MyCart;
import nlu.hmuaf.android_bookapp.user.home.adapter.DiscountAdapter;
import nlu.hmuaf.android_bookapp.user.home.adapter.NewBookAdapter;
import nlu.hmuaf.android_bookapp.user.home.adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;
import nlu.hmuaf.android_bookapp.user.profile.activity.ProfileActivity;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rcv1Data;
    private RecyclerView rcv2Data;
    private DiscountAdapter imageAdapter;
    private NewBookAdapter popularAdapter;
    private BookAppApi bookAppApi;
    private List<ListBookResponseDTO> newListBook, discountListBook = new ArrayList<>();
    private ProgressBar progressBar;
    private final int SIZE = 30;
    private TextView welcomeTextView;
    private CartService cartService;
    private TokenResponseDTO tokenResponse;
    private TextView cartBadgeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        rcv1Data = findViewById(R.id.rcv1_Data);
        rcv2Data = findViewById(R.id.rcv2_Data);
        progressBar = findViewById(R.id.progressBar);
        // get the barget text view
        cartBadgeTextView = findViewById(R.id.cart_badge_text_view);
        // get data from api
        getDiscountBookData();
        getNewBookData();

        // get api key
        getApiGoogleKey();
        // get the cart service
        cartService = new CartService(getApplicationContext());

        // đăng ký sự kiện observe để render lại sự thay đổi của giỏ hàng
        cartService.getCartSizeLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cartSize) {
                updateCartQuantity(cartSize);
            }
        });

        welcomeTextView = findViewById(R.id.welcome_text_view);

        tokenResponse = MyUtils.getTokenResponse(this);

        if (tokenResponse != null) {
            String welcomeMessage = "Xin chào, " + tokenResponse.getUsername() + "!";
            welcomeTextView.setText(welcomeMessage);
        } else {
            welcomeTextView.setText("Xin chào");
        }

        // Tìm TextView theo ID
        ImageView tvPrevious = findViewById(R.id.tv_previous);
        TextView textViewViewMore = findViewById(R.id.textViewViewMore);
        textViewViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang search với tiêu chí đang giảm giá khi click vào chữ "viewMore"
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                // Truyền thông tin lựa chọn filter qua Intent
                intent.putExtra("filterType", "discountBook");
                startActivity(intent);
            }
        });
        TextView textViewViewMore2 = findViewById(R.id.textViewViewMore2);
        textViewViewMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang search với tiêu chí sách mới nhập khi click vào chữ "viewMore"
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                // Truyền thông tin lựa chọn filter qua Intent
                intent.putExtra("filterType", "newBook");
                startActivity(intent);
            }
        });
        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing or perform another action
            }
        });

        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        cartActionInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isUserLoggedIn(HomeActivity.this)) {
                    Intent intent = new Intent(HomeActivity.this, MyCart.class);
                    startActivity(intent);
                }
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv1Data.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv2Data.setLayoutManager(layoutManager2);

        imageAdapter = new DiscountAdapter(discountListBook, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ListBookResponseDTO selectedBook = discountListBook.get(position);
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("BOOK_ID", selectedBook.getBookId());
                startActivity(intent);
            }

        }, new DiscountAdapter.OnPriceClickListener() {
            //Khi bấm vào giá tiền thì thêm vào giỏ hàng
            @Override
            public void onPriceClick(int position) {
                if (MyUtils.isUserLoggedIn(HomeActivity.this)) {
                    ListBookResponseDTO selectedBook = discountListBook.get(position);
                    cartService.updateProductCart(tokenResponse, selectedBook, 1);
                    // add animation
                    ImageView imageView = findImageViewForDiscountBook(selectedBook);
                    if (imageView != null) {
                        makeFlyAnimation(imageView);
                    }
                }
            }
        });
        rcv1Data.setAdapter(imageAdapter);
        // get new book
        popularAdapter = new NewBookAdapter(newListBook, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ListBookResponseDTO selectedBook = newListBook.get(position);
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("BOOK_ID", selectedBook.getBookId());
                startActivity(intent);
            }
        }, new NewBookAdapter.OnPriceClickListener() {
            @Override
            public void onPriceClick(int position) {
                if (MyUtils.isUserLoggedIn(HomeActivity.this)) {
                    ListBookResponseDTO selectedBook = newListBook.get(position);
                    cartService.updateProductCart(tokenResponse, selectedBook, 1);

                    // add animation
                    ImageView imageView = findImageViewForNewBook(selectedBook);
                    if (imageView != null) {
                        makeFlyAnimation(imageView);
                    }
                }
            }
        });
        rcv2Data.setAdapter(popularAdapter);

        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        // Set click listeners for footer
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                if (tokenResponse != null) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, LogOutActivity.class);
                    startActivity(intent);
                }
            }
        });
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchActivity
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isUserLoggedIn(HomeActivity.this)) {
                    // Navigate to HomeActivity
                    Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
                    startActivity(intent);
                }
            }
        });

        // lấy số lượng sách trong giỏ hàng
        updateCartQuantity();

        //  Kiểm tra activity hiện tại để đổi màu icon ở navigation
        checkCurrentActivity();

    }

    public void getApiGoogleKey() {
        if (MyUtils.getApiKey(getApplicationContext()) == null) {
            bookAppApi = BookAppService.getClient();
            Call<APIKeyResponse> call = bookAppApi.getGoogleMapAPIKey();
            call.enqueue(new Callback<APIKeyResponse>() {
                @Override
                public void onResponse(Call<APIKeyResponse> call, Response<APIKeyResponse> response) {
                    System.out.println("API KEY NE");
                    if (response.isSuccessful()) {
                        APIKeyResponse apiKeyResponse = response.body();
                        String apiKey = apiKeyResponse.getApiKey();
                        MyUtils.saveApiKey(getApplicationContext(), apiKey); // Store the API key in SharedPreferences
                    } else {
                        System.out.println(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<APIKeyResponse> call, Throwable throwable) {
                    // Handle failure
                    System.out.println(throwable.getMessage());
                }
            });
        }
    }

    private void updateCartQuantity() {
        if (MyUtils.getTokenResponse(this) != null) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Lấy số lượng sản phẩm trong giỏ hàng từ cơ sở dữ liệu
                int quantityBookInCart = cartService.getUserCart(tokenResponse.getUserId()).size();

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


    // get discount book in shipment
    public void getDiscountBookData() {
        progressBar.setVisibility(View.VISIBLE);
        bookAppApi = BookAppService.getClient();
        Call<List<ListBookResponseDTO>> call = bookAppApi.getDiscountListBooks(0, SIZE);
        call.enqueue(new Callback<List<ListBookResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListBookResponseDTO>> call, Response<List<ListBookResponseDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    // Lấy danh sách sách mới từ phản hồi
                    discountListBook = (List<ListBookResponseDTO>) response.body();
                    // Cập nhật RecyclerView với danh sách sách mới
                    updateDiscountListBookRecyclerView(discountListBook);
                } else {
                    // Xử lý lỗi khi không thành công
                    System.out.println("lỗi");
                }
            }

            @Override
            public void onFailure(Call<List<ListBookResponseDTO>> call, Throwable throwable) {
                // Xử lý lỗi khi gọi API thất bại
                System.out.println(throwable.getMessage());
            }
        });
    }

    // get new book in shipment
    public void getNewBookData() {
        progressBar.setVisibility(View.VISIBLE);
        bookAppApi = BookAppService.getClient();
        Call<List<ListBookResponseDTO>> call = bookAppApi.getNewListBooks(0, SIZE);
        call.enqueue(new Callback<List<ListBookResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListBookResponseDTO>> call, Response<List<ListBookResponseDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    // Lấy danh sách sách mới từ phản hồi
                    newListBook = response.body();
                    // Cập nhật RecyclerView với danh sách sách mới
                    updateNewBookRecyclerView(newListBook);
                } else {
                    // Xử lý lỗi khi không thành công
                    System.out.println("lỗi lấy data");
                }
            }

            @Override
            public void onFailure(Call<List<ListBookResponseDTO>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                // Xử lý lỗi khi gọi API thất bại
                System.out.println(throwable.getMessage());
            }
        });
    }

    private void updateDiscountListBookRecyclerView(List<ListBookResponseDTO> discountListBook) {
        if (imageAdapter == null) {
            imageAdapter = new DiscountAdapter(discountListBook, new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ListBookResponseDTO selectedBook = discountListBook.get(position);
                    Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                    intent.putExtra("BOOK_ID", selectedBook.getBookId());
                    startActivity(intent);
                }
            }, new DiscountAdapter.OnPriceClickListener() {
                // của Tú
                @Override
                public void onPriceClick(int position) {
                    if (MyUtils.isUserLoggedIn(HomeActivity.this)) {
                        ListBookResponseDTO selectedBook = discountListBook.get(position);
                        cartService.updateProductCart(tokenResponse, selectedBook, 1);

                        // add animation
                        ImageView imageView = findImageViewForDiscountBook(selectedBook);
                        if (imageView != null) {
                            makeFlyAnimation(imageView);
                        }
                    }
                }
            });
            rcv1Data.setAdapter(imageAdapter);
        } else {
            // Cập nhật dữ liệu mới cho adapter và thông báo thay đổi
            imageAdapter.updateData(discountListBook);
        }
    }

    // Phương thức này sẽ cập nhật RecyclerView với danh sách sách mới
    private void updateNewBookRecyclerView(List<ListBookResponseDTO> newListBooks) {
        if (popularAdapter == null) {
            // Khởi tạo adapter nếu chưa có
            popularAdapter = new NewBookAdapter(newListBooks, new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ListBookResponseDTO selectedBook = newListBooks.get(position);
                    // Xử lý sự kiện khi người dùng nhấn vào một sách
                    Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                    intent.putExtra("BOOK_ID", selectedBook.getBookId());
                    startActivity(intent);
                }
            }, new NewBookAdapter.OnPriceClickListener() {
                @Override
                public void onPriceClick(int position) {
                    if (MyUtils.isUserLoggedIn(HomeActivity.this)) {
                        ListBookResponseDTO selectedBook = newListBooks.get(position);
                        cartService.updateProductCart(tokenResponse, selectedBook, 1);

                        // add animation
                        ImageView imageView = findImageViewForNewBook(selectedBook);
                        if (imageView != null) {
                            makeFlyAnimation(imageView);
                        }
                    }
                }
            });
            rcv2Data.setAdapter(popularAdapter);
        } else {
            // Cập nhật dữ liệu mới cho adapter và thông báo thay đổi
            popularAdapter.updateData(newListBooks);
        }
    }

    private void updateCartQuantity(int cartSize) {
        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
        quantityTextView.setText(String.valueOf(cartSize));
        quantityTextView.setVisibility(View.VISIBLE);
    }

    public void checkCurrentActivity() {
        Activity currentActivity = (Activity) this;
        if (currentActivity instanceof HomeActivity) {
            // Đây là HomeActivity
            LinearLayout homeLayout = findViewById(R.id.homeLayout);
            ImageView imageView = homeLayout.findViewById(R.id.homeIcon);
            TextView textView = homeLayout.findViewById(R.id.txt_homeIcon);

            String colorString = "#B868E9";
            int color = Color.parseColor(colorString);
            imageView.setColorFilter(color);
            textView.setTextColor(color);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(14);
        }
    }

    private ImageView findImageViewForDiscountBook(ListBookResponseDTO selectedBook) {
        // start find
        for (int i = 0; i < rcv1Data.getChildCount(); i++) {
            View childView = rcv1Data.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = rcv1Data.findContainingViewHolder(childView);
            if (viewHolder instanceof DiscountAdapter.ImageViewHolder) {
                DiscountAdapter.ImageViewHolder imageViewHolder = (DiscountAdapter.ImageViewHolder) viewHolder;
                if (discountListBook != null && imageViewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    ListBookResponseDTO book = discountListBook.get(imageViewHolder.getAdapterPosition());
                    if (book != null && book.getBookId() == selectedBook.getBookId()) {
                        return imageViewHolder.getImgBook();
                    }
                }
            }
        }
        return null;
    }

    private ImageView findImageViewForNewBook(ListBookResponseDTO selectedBook) {
        // start find
        for (int i = 0; i < rcv2Data.getChildCount(); i++) {
            View childView = rcv2Data.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = rcv2Data.findContainingViewHolder(childView);
            if (viewHolder instanceof NewBookAdapter.PopularViewHolder) {
                NewBookAdapter.PopularViewHolder newBookViewHolder = (NewBookAdapter.PopularViewHolder) viewHolder;
                if (newListBook != null && newBookViewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    ListBookResponseDTO book = newListBook.get(newBookViewHolder.getAdapterPosition());
                    if (book != null && book.getBookId() == selectedBook.getBookId()) {
                        return newBookViewHolder.getImgBook();
                    }
                }
            }
        }
        return null;
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
                Toast.makeText(HomeActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();

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
