package nlu.hmuaf.android_bookapp.user.home.Activity;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.animation.add_to_cart.CircleAnimation;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.Activity.MyCart;
import nlu.hmuaf.android_bookapp.user.home.Adapter.BookAdapter;
import nlu.hmuaf.android_bookapp.user.home.Adapter.NewBookAdapter;
import nlu.hmuaf.android_bookapp.user.home.Adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private TokenResponseDTO tokenResponse;
    private BookAppApi bookAppApi;
    private RecyclerView rcvBook;
    private BookAdapter bookAdapter;
    private Button filterButton;
    private Button btnPrevious, btnNext;
    private TextView tvPageNumber;
    private ProgressBar progressBar;
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 10;
    private int quantityBookInCart = 0;
    private List<ListBookResponseDTO> listBookResponse = new ArrayList<>();
    private CartService cartService;
    private View searchView;
    private ImageView buttonSearch;
    private EditText valueSearch;
    private String selectedFormatFilter = "";
    private String selectedTypeFilter = "";
    private String selectedPublisherFilter = "";
    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sreach_activity);
        progressBar = findViewById(R.id.progressBar);
        // get data from api
        bookAppApi = BookAppService.getClient();
        findBook(searchText, currentPage, ITEMS_PER_PAGE, selectedFormatFilter, selectedTypeFilter, selectedPublisherFilter);
        ImageView tvPrevious = findViewById(R.id.tv_previous);


        // handle search
        searchView = findViewById(R.id.sreach2_layout);
        buttonSearch = searchView.findViewById(R.id.searchBtn);
        valueSearch = searchView.findViewById(R.id.valueSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = valueSearch.getText().toString();
                currentPage = 0;
                findBook(searchText, currentPage, ITEMS_PER_PAGE, selectedFormatFilter, selectedTypeFilter, selectedPublisherFilter);
            }
        });

        tokenResponse = MyUtils.getTokenResponse(this);
        rcvBook = findViewById(R.id.BookNameRecyclerView);

        // get the cart service
        cartService = new CartService(getApplicationContext());
        // đăng ký sự kiện observe để render lại sự thay đổi của giỏ hàng
        cartService.getCartSizeLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cartSize) {
                updateCartQuantity(cartSize);
            }
        });
        // my filter dialog
        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        FrameLayout cartActionInclude = findViewById(R.id.cartItem);
        cartActionInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isUserLoggedIn(SearchActivity.this)) {
                    Intent intent = new Intent(SearchActivity.this, MyCart.class);
                    startActivity(intent);
                }
            }
        });

        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        tvPageNumber = findViewById(R.id.tvPageNumber);

        // Set OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại để quay lại trang trước
            }
        });

        // Khởi tạo và thiết lập LinearLayoutManager cho RecyclerViews
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvBook.setLayoutManager(layoutManager);

        // Khởi tạo Adapter và đặt Adapter cho RecyclerViews
        bookAdapter = new BookAdapter(listBookResponse, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ListBookResponseDTO selectedBook = listBookResponse.get(position);
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("BOOK_ID", selectedBook.getBookId());
                startActivity(intent);
            }
        }, new BookAdapter.OnAddToCartClick() {
            @Override
            public void onCartClick(int position) {
                if (MyUtils.isUserLoggedIn(SearchActivity.this)) {
                    System.out.println("Test cart");
                    ListBookResponseDTO selectedBook = listBookResponse.get(position);
                    System.out.println(selectedBook);
                    cartService.updateProductCart(tokenResponse, selectedBook, 1);
                    System.out.println("ok");
                    // add animation
                    ImageView imageView = findImageViewForListBook(selectedBook);
                    if (imageView != null) {
                        makeFlyAnimation(imageView);
                    }
                }
            }
        });
        rcvBook.setAdapter(bookAdapter);
        setView();
        // Set click listeners for footer
        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, LogOutActivity.class);
                startActivity(intent);
            }
        });

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    findBook(valueSearch.getText().toString(), currentPage, ITEMS_PER_PAGE, selectedFormatFilter, selectedTypeFilter, selectedPublisherFilter);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                findBook(valueSearch.getText().toString(), currentPage, ITEMS_PER_PAGE, selectedFormatFilter, selectedTypeFilter, selectedPublisherFilter);
            }
        });
        // lấy số lượng sách trong giỏ hàng
        updateCartQuantity();

        // Đổi màu icon navigation hiện tại
        checkCurrentActivity();
    }

    public void checkCurrentActivity() {
        Activity currentActivity = (Activity) this;
        if (currentActivity instanceof SearchActivity) {
            // Đây là HomeActivity
            LinearLayout homeLayout = findViewById(R.id.searchLayout);
            ImageView imageView = homeLayout.findViewById(R.id.searchIcon);
            TextView textView = homeLayout.findViewById(R.id.txt_searchIcon);

            String colorString = "#B868E9";
            int color = Color.parseColor(colorString);
            imageView.setColorFilter(color);
            textView.setTextColor(color);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(14);
        }
    }


    private void showFilterDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filter_dialog, null);

        RadioGroup radioGroupFormat = dialogView.findViewById(R.id.radioGroupFormat);
        RadioGroup radioGroupLoaiSach = dialogView.findViewById(R.id.radioGroupLoaiSach);
        RadioGroup radioGroupNxb = dialogView.findViewById(R.id.radioGroupNxb);

        new AlertDialog.Builder(this)
                .setTitle("Filter Options")
                .setView(dialogView)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedFormatId = radioGroupFormat.getCheckedRadioButtonId();
                        selectedFormatFilter = getSelectedFilter(selectedFormatId);

                        int selectedLoaiSachId = radioGroupLoaiSach.getCheckedRadioButtonId();
                        selectedTypeFilter = getSelectedFilter(selectedLoaiSachId);

                        int selectedNxbId = radioGroupNxb.getCheckedRadioButtonId();
                        selectedPublisherFilter = getSelectedFilter(selectedNxbId);

                        findBook(valueSearch.getText().toString(), currentPage, ITEMS_PER_PAGE, selectedFormatFilter, selectedTypeFilter, selectedPublisherFilter);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String getSelectedFilter(int selectedId) {
        if (selectedId == R.id.radioButtonBiaCung) {
            return "HARDCOVER";
        } else if (selectedId == R.id.radioButtonBiaMem) {
            return "PAPERBACK";
        } else if (selectedId == R.id.radioButtonSachMoi) {
            return "newBook";
        } else if (selectedId == R.id.radioButtonGiamGia) {
            return "discountBook";
        } else if (selectedId == R.id.radioButtonNxbKimDong) {
            return "NxbKimDong";
        } else if (selectedId == R.id.radioButtonNxbTre) {
            return "NxbTre";
        } else if (selectedId == R.id.radioButtonNxbHoc) {
            return "NxbHoc";
        } else if (selectedId == R.id.radioButtonNxbHaNoi) {
            return "NxbHaNoi";
        } else if (selectedId == R.id.radioButtonNxbVanHoc) {
            return "NxbVanHoc";
        } else if (selectedId == R.id.radioButtonNxbTheGioi) {
            return "NxbTheGioi";
        } else if (selectedId == R.id.radioButtonYenOn) {
            return "YenOn";
        } else {
            return "";
        }
    }

    public void findBook(String searchText, int page, int size, String formatFilter, String typeFilter, String publisherFilter) {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<ListBookResponseDTO>> call = bookAppApi.findBooks(searchText, formatFilter, typeFilter, publisherFilter, page, size);
        call.enqueue(new Callback<List<ListBookResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListBookResponseDTO>> call, Response<List<ListBookResponseDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    listBookResponse = response.body();
                    updateListBookRecyclerView(listBookResponse);
                    tvPageNumber.setText(String.valueOf(currentPage + 1));
                } else {
                    Toast.makeText(SearchActivity.this, "Error loading books", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListBookResponseDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateListBookRecyclerView(List<ListBookResponseDTO> listBook) {
        if (bookAdapter == null) {
            bookAdapter = new BookAdapter(listBook, new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ListBookResponseDTO selectedBook = listBook.get(position);
                    Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                    intent.putExtra("BOOK_ID", selectedBook.getBookId());
                    startActivity(intent);
                }
            }, new BookAdapter.OnAddToCartClick() {
                @Override
                public void onCartClick(int position) {
                    if (MyUtils.isUserLoggedIn(SearchActivity.this)) {
                        ListBookResponseDTO selectedBook = listBookResponse.get(position);
                        cartService.updateProductCart(tokenResponse, selectedBook, 1);

                        // add animation
                        ImageView imageView = findImageViewForListBook(selectedBook);
                        if (imageView != null) {
                            makeFlyAnimation(imageView);
                        }
                    }
                }
            });
            rcvBook.setAdapter(bookAdapter);
        } else {
            // Cập nhật dữ liệu mới cho adapter và thông báo thay đổi
            bookAdapter.updateData(listBook);
        }
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

    private ImageView findImageViewForListBook(ListBookResponseDTO selectedBook) {
        // start find
        for (int i = 0; i < rcvBook.getChildCount(); i++) {
            View childView = rcvBook.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = rcvBook.findContainingViewHolder(childView);
            if (viewHolder instanceof BookAdapter.BookViewHolder) {
                BookAdapter.BookViewHolder bookViewHolder = (BookAdapter.BookViewHolder) viewHolder;
                if (listBookResponse != null && bookViewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    ListBookResponseDTO book = listBookResponse.get(bookViewHolder.getAdapterPosition());
                    if (book != null && book.getBookId() == selectedBook.getBookId()) {
                        return bookViewHolder.getImgBook();
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
                Toast.makeText(SearchActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();

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

    private void setView() {
        // DividerItemDecoration với khoảng cách tùy chỉnh
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvBook.getContext(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));

        // Thêm ItemDecoration cho từng RecyclerView
        rcvBook.addItemDecoration(dividerItemDecoration);
        rcvBook.setVisibility(View.VISIBLE);
    }
}

