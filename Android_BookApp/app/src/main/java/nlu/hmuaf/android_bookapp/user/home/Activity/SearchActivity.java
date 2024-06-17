    package nlu.hmuaf.android_bookapp.user.home.Activity;

    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.util.TypedValue;
    import android.view.View;
    import android.widget.Button;
    import android.widget.FrameLayout;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.content.ContextCompat;
    import androidx.recyclerview.widget.DividerItemDecoration;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import java.util.ArrayList;
    import java.util.List;
    import nlu.hmuaf.android_bookapp.user.home.Adapter.AuthorAdapter;
    import nlu.hmuaf.android_bookapp.user.home.Adapter.BookAdapter;
    import nlu.hmuaf.android_bookapp.user.home.Adapter.BookMAdapter;
    import nlu.hmuaf.android_bookapp.user.home.Adapter.OnItemClickListener;
    import nlu.hmuaf.android_bookapp.user.home.Adapter.OtherAdapter;
    import nlu.hmuaf.android_bookapp.user.home.Class.BookB;
    import nlu.hmuaf.android_bookapp.user.home.Class.Publisher;
    import nlu.hmuaf.android_bookapp.R;
    import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;

    public class SearchActivity extends AppCompatActivity {

        private RecyclerView rcvAuthor, rcvBookC, rcvOther, rcvBookM;
        private AuthorAdapter authorAdapter;
        private BookAdapter bookAdapter;
        private OtherAdapter otherAdapter;
        private BookMAdapter bookMAdapter;
        private Button btnTG, btnBC, btnBM, btnNSX;
        private Button btnPrevious, btnNext;
        private TextView tvPageNumber;
        private int currentPage = 0;
        private static final int ITEMS_PER_PAGE = 10;
        private int quantityBookInCart = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sreach_activity);

            TextView tvPrevious = findViewById(R.id.tv_previous);

            rcvBookC = findViewById(R.id.BookNameRecyclerView);
            rcvBookM = findViewById(R.id.BookNameRecyclerViewM);
            rcvOther = findViewById(R.id.OtherRecyclerView);

            btnBC = findViewById(R.id.buttonBiaCung);
            btnBM = findViewById(R.id.buttonBiaMem);
            btnNSX = findViewById(R.id.buttonNhaXuatBan);
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
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rcvBookC.setLayoutManager(layoutManager);
            rcvOther.setLayoutManager(new LinearLayoutManager(this));
            rcvBookM.setLayoutManager(new LinearLayoutManager(this));

            // DividerItemDecoration với khoảng cách tùy chỉnh
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvBookC.getContext(), LinearLayoutManager.VERTICAL);
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));

            // Thêm ItemDecoration cho từng RecyclerView
            rcvBookC.addItemDecoration(dividerItemDecoration);
            rcvOther.addItemDecoration(dividerItemDecoration);
            rcvBookM.addItemDecoration(dividerItemDecoration);

            rcvBookC.setVisibility(View.VISIBLE);
            rcvOther.setVisibility(View.GONE);
            rcvBookM.setVisibility(View.GONE);

            btnBC.setSelected(true);
            btnBM.setSelected(false);
            btnNSX.setSelected(false);

            btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp
            btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp
            btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp

            // Khởi tạo Adapter và đặt Adapter cho RecyclerViews
            bookMAdapter = new BookMAdapter(getListBookM(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                    intent.putExtra("book_position", position);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    startActivity(intent);
                }
            });
            rcvBookM.setAdapter(bookMAdapter);

            bookAdapter = new BookAdapter(getListBookN(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                    intent.putExtra("book_position", position);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    startActivity(intent);
                }
            });
            rcvBookC.setAdapter(bookAdapter);

            otherAdapter = new OtherAdapter(getListOther(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(SearchActivity.this, FantasyActivity.class);
                    intent.putExtra("book_position", position);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    startActivity(intent);
                }
            });
            rcvOther.setAdapter(otherAdapter);

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
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    startActivity(intent);
                }
            });

            searchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    startActivity(intent);
                }
            });

            libraryLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, LibraryActivity.class);
                    intent.putExtra("quantityBookInCart", quantityBookInCart);
                    startActivity(intent);
                }
            });

            btnBC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rcvBookC.setVisibility(View.VISIBLE);
                    rcvOther.setVisibility(View.GONE);
                    rcvBookM.setVisibility(View.GONE);

                    btnBC.setSelected(true);
                    btnBM.setSelected(false);
                    btnNSX.setSelected(false);

                    btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp
                    btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp
                    btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp

                    currentPage = 0;
                    updateRecyclerView();
                }
            });

            btnBM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rcvBookC.setVisibility(View.GONE);
                    rcvOther.setVisibility(View.GONE);
                    rcvBookM.setVisibility(View.VISIBLE);

                    btnBC.setSelected(false);
                    btnBM.setSelected(true);
                    btnNSX.setSelected(false);

                    btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp
                    btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp
                    btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp

                    currentPage = 0;
                    updateRecyclerView();
                }
            });

            btnNSX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rcvBookC.setVisibility(View.GONE);
                    rcvOther.setVisibility(View.VISIBLE);
                    rcvBookM.setVisibility(View.GONE);

                    btnBC.setSelected(false);
                    btnBM.setSelected(false);
                    btnNSX.setSelected(true);

                    btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp
                    btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 12sp
                    btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp

                    currentPage = 0;
                    updateRecyclerView();
                }
            });

            btnPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentPage > 0) {
                        currentPage--;
                        updateRecyclerView();
                    }
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((currentPage + 1) * ITEMS_PER_PAGE < getCurrentListSize()) {
                        currentPage++;
                        updateRecyclerView();
                    }
                }
            });

            updateRecyclerView();
//            Cập nhập số lượng sách trong giỏ hàng
            updateQuantityBookInCart();
//            Đổi màu icon navigation hiện tại
            checkCurrentActivity();
        }

        private void updateRecyclerView() {
            if (btnBC.isSelected()) {
                bookAdapter.updateDisplayList(currentPage);
            } else if (btnBM.isSelected()) {
                bookMAdapter.updateDisplayList(currentPage);
            } else if (btnNSX.isSelected()) {
                otherAdapter.updateDisplayList(currentPage);
            }
            tvPageNumber.setText(getString(R.string.page_number, currentPage + 1));
        }

        private int getCurrentListSize() {
            if (btnBC.isSelected()) {
                return getListBookN().size();
            } else if (btnBM.isSelected()) {
                return getListBookM().size();
            } else if (btnNSX.isSelected()) {
                return getListOther().size();
            }
            return 0;
        }

        private List<BookB> getListBookM() {
            List<BookB> list = new ArrayList<>();
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            return list;
        }

        private List<BookB> getListBookN() {
            List<BookB> list = new ArrayList<>();
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            list.add(new BookB(R.drawable.book1, "book 1", "500$"));
            list.add(new BookB(R.drawable.book2, "book 2", "500$"));
            list.add(new BookB(R.drawable.book3, "book 3", "500$"));
            list.add(new BookB(R.drawable.book4, "book 4", "500$"));
            list.add(new BookB(R.drawable.book5, "book 5", "500$"));
            return list;
        }

        private List<Publisher> getListOther() {
            List<Publisher> list = new ArrayList<>();
            list.add(new Publisher(R.drawable.book2, "NXB Kim Đồng", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book3, "NXB Hà Nội", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book4, "NXB Văn Học", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book5, "NXB Ye On", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book1, "NXB Thế Giới", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book2, "NXB Kim Đồng", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book3, "NXB Hà Nội", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book4, "NXB Văn Học", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book5, "NXB Ye On", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book1, "NXB Thế Giới", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book2, "NXB Kim Đồng", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book3, "NXB Hà Nội", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book4, "NXB Văn Học", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book5, "NXB Ye On", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            list.add(new Publisher(R.drawable.book1, "NXB Thế Giới", "123/2 Nguyễn Thị Minh Khai TpHCM", "1900 8088"));
            return list;
        }
        //    Cập nhập số sách trong giỏ hàng
        private void updateQuantityBookInCart(){
            quantityBookInCart = getIntent().getIntExtra("quantityBookInCart", 0);
            FrameLayout cartActionInclude = findViewById(R.id.cartItem);
            TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
            quantityTextView.setText(String.valueOf(quantityBookInCart));
            quantityTextView.setVisibility(quantityBookInCart == 0 ? View.GONE : View.VISIBLE);
        }
        public int getQuantityBookInCart() {
            return quantityBookInCart;
        }

        public void setQuantityBookInCart(int quantityBookInCart) {
            this.quantityBookInCart = quantityBookInCart;
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
    }

