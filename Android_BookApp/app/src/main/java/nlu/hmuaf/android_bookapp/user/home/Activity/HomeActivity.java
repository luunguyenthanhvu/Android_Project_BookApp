package nlu.hmuaf.android_bookapp.user.home.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.Activity.MyCart;
import nlu.hmuaf.android_bookapp.user.home.Adapter.DiscountAdapter;
import nlu.hmuaf.android_bookapp.user.home.Adapter.NewBookAdapter;
import nlu.hmuaf.android_bookapp.user.home.Adapter.OnItemClickListener;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;
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
    private int quantityBookInCart = 0;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
//        Cập nhập số lượng sách trong giỏ hàng
        updateCartQuantityFromOtherActivity();
        rcv1Data = findViewById(R.id.rcv1_Data);
        rcv2Data = findViewById(R.id.rcv2_Data);
        progressBar = findViewById(R.id.progressBar);

        welcomeTextView = findViewById(R.id.welcome_text_view);

        TokenResponseDTO tokenResponse = MyUtils.getTokenResponse(this);

        if (tokenResponse != null) {
            String welcomeMessage = "Xin chào, " + tokenResponse.getUsername() + "!";
            welcomeTextView.setText(welcomeMessage);
        } else {
            welcomeTextView.setText("Xin chào");
        }

        // get data from api
        getDiscountBookData();
        getNewBookData();

        // Tìm TextView theo ID
        TextView tvPrevious = findViewById(R.id.tv_previous);
        TextView textViewViewMore = findViewById(R.id.textViewViewMore);
        textViewViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang FantasyActivity khi click vào chữ "viewMore"
                Intent intent = new Intent(HomeActivity.this, FantasyActivity.class);
                startActivity(intent);
            }
        });
        TextView textViewViewMore2 = findViewById(R.id.textViewViewMore2);
        textViewViewMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang FantasyActivity khi click vào chữ "viewMore"
                Intent intent = new Intent(HomeActivity.this, FantasyActivity.class);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv1Data.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv2Data.setLayoutManager(layoutManager2);

        imageAdapter = new DiscountAdapter(discountListBook, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                intent.putExtra("quantityBookInCart", quantityBookInCart);
                startActivity(intent);
            }
        }, new DiscountAdapter.OnPriceClickListener() {
            //            Khi nhấn vào giá tiền giảm giá thì sách sẽ được vào giỏ hàng
            @Override
            public void onPriceClick(int position) {
                ListBookResponseDTO selectedBook = discountListBook.get(position);
                quantityBookInCart++;
                updateCartQuantity(quantityBookInCart);
            }
        });
        rcv1Data.setAdapter(imageAdapter);

        // get new book
        popularAdapter = new NewBookAdapter(newListBook, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                intent.putExtra("quantityBookInCart", quantityBookInCart);
                startActivity(intent);
            }
        }, new NewBookAdapter.OnPriceClickListener() {
//            Khi nhấn vào giá tiền thì sách sẽ được vào giỏ hàng
            @Override
            public void onPriceClick(int position) {
                ListBookResponseDTO selectedBook = newListBook.get(position);
                quantityBookInCart++;
                updateCartQuantity(quantityBookInCart);

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
                Intent intent = new Intent(HomeActivity.this, LogOutActivity.class);
                startActivity(intent);
            }
        });
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                intent.putExtra("quantityBookInCart", getQuantityBookInCart());
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchActivity
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("quantityBookInCart", getQuantityBookInCart());
                startActivity(intent);
            }
        });
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
                intent.putExtra("quantityBookInCart", getQuantityBookInCart());
                startActivity(intent);
            }
        });
//        Xử lý sách đã chọn mua vào giỏ hàng
        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
        quantityTextView.setText(String.valueOf(quantityBookInCart));
        quantityTextView.setVisibility(quantityBookInCart == 0 ? View.GONE : View.VISIBLE);
        cartActionInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyCart.class);
                startActivity(intent);
            }
        });
//        Kiểm tra activity hiện tại để đổi màu icon ở navigation
        checkCurrentActivity();
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
                    newListBook = (List<ListBookResponseDTO>) response.body();
                    // Cập nhật RecyclerView với danh sách sách mới
                    updateNewBookRecyclerView(newListBook);
                } else {
                    // Xử lý lỗi khi không thành công
                    System.out.println("lỗi");
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
                    Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    intent.putExtra("book_position", position);
                    startActivity(intent);
                }
            }, new DiscountAdapter.OnPriceClickListener() {
                @Override
                public void onPriceClick(int position) {
                    ListBookResponseDTO selectedBook = discountListBook.get(position);
                    quantityBookInCart++;
                    updateCartQuantity(quantityBookInCart);
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
                    // Xử lý sự kiện khi người dùng nhấn vào một sách
                    Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    intent.putExtra("book_position", position);
                    startActivity(intent);
                }
            }, new NewBookAdapter.OnPriceClickListener() {
                @Override
                public void onPriceClick(int position) {
                    ListBookResponseDTO selectedBook = newListBooks.get(position);
                    quantityBookInCart++;
                    updateCartQuantity(quantityBookInCart);
                    // Xử lý thêm sách vào giỏ hàng hoặc bất kỳ hành động nào khác
                }
            });
            rcv2Data.setAdapter(popularAdapter);
        } else {
            // Cập nhật dữ liệu mới cho adapter và thông báo thay đổi
            popularAdapter.updateData(newListBooks);
        }
    }
    private void updateCartQuantity(int quantityBookInCart) {
        quantityBookInCart = quantityBookInCart;
        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
        quantityTextView.setText(String.valueOf(quantityBookInCart));
        quantityTextView.setVisibility(quantityBookInCart == 0 ? View.GONE : View.VISIBLE);
    }
    private void updateCartQuantityFromOtherActivity() {
        setQuantityBookInCart(getIntent().getIntExtra("quantityBookInCart", 0));
        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
        quantityTextView.setText(String.valueOf(getQuantityBookInCart()));
        quantityTextView.setVisibility(getQuantityBookInCart() == 0 ? View.GONE : View.VISIBLE);
    }
    public int getQuantityBookInCart() {
        return quantityBookInCart;
    }

    public void setQuantityBookInCart(int quantityBookInCart) {
        this.quantityBookInCart = quantityBookInCart;
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

}
