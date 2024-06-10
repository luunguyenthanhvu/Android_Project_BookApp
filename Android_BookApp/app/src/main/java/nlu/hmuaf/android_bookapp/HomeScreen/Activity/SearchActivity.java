package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.AuthorAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.BookAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.NSXAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OtherAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.Author;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.activity.LogOutActivity;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcvAuthor, rcvBookC, rcvOther,rcvNSX;
    private AuthorAdapter authorAdapter;
    private BookAdapter bookAdapter;
    private OtherAdapter otherAdapter;
    private NSXAdapter nsxAdapter;
    private Button btnTG,btnBC,btnBM,btnNSX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sreach_activity);

        TextView tvPrevious = findViewById(R.id.tv_previous);
        rcvAuthor = findViewById(R.id.AuthorRecyclerView);
        rcvBookC = findViewById(R.id.BookNameRecyclerView);
        rcvNSX=  findViewById(R.id.BookNameRecyclerViewM);
        rcvOther = findViewById(R.id.OtherRecyclerView);

        btnTG = findViewById(R.id.buttonTacGia);
        btnBC = findViewById(R.id.buttonBiaCung);
        btnBM = findViewById(R.id.buttonBiaMem);
        btnNSX = findViewById(R.id.buttonNhaXuatBan);
        // Set OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại để quay lại trang trước
            }
        });

        // Khởi tạo và thiết lập LinearLayoutManager cho RecyclerViews
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvAuthor.setLayoutManager(layoutManager);
        rcvBookC.setLayoutManager(new LinearLayoutManager(this));
        rcvOther.setLayoutManager(new LinearLayoutManager(this));
        rcvNSX.setLayoutManager(new LinearLayoutManager(this));

        rcvAuthor.setVisibility(View.VISIBLE);
        rcvBookC.setVisibility(View.GONE);
        rcvOther.setVisibility(View.GONE);
        rcvNSX.setVisibility(View.GONE);

        btnTG.setSelected(true);
        btnBC.setSelected(false);
        btnBM.setSelected(false);
        btnNSX.setSelected(false);

        btnTG.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp
        btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
        btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
        btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
        // Khởi tạo Adapter và đặt Adapter cho RecyclerViews
        authorAdapter = new AuthorAdapter(getListAuthor(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcvAuthor.setAdapter(authorAdapter);
        nsxAdapter = new NSXAdapter(getListBookM(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
       rcvNSX.setAdapter(nsxAdapter);
        bookAdapter = new BookAdapter(getListBookN(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcvBookC.setAdapter(bookAdapter);

        otherAdapter = new OtherAdapter(getListOther(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, FantasyActivity.class);
                intent.putExtra("book_position", position);
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
        btnTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAuthor.setVisibility(View.VISIBLE);
                rcvBookC.setVisibility(View.GONE);
                rcvOther.setVisibility(View.GONE);
                rcvNSX.setVisibility(View.GONE);
                btnTG.setSelected(true);
                btnBC.setSelected(false);
                btnBM.setSelected(false);
                btnNSX.setSelected(false);

                btnTG.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp
                btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
                btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
                btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
            }
        });

        btnBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAuthor.setVisibility(View.GONE);
                rcvBookC.setVisibility(View.VISIBLE);
                rcvOther.setVisibility(View.GONE);
                rcvNSX.setVisibility(View.GONE);
                btnTG.setSelected(false);
                btnBC.setSelected(true);
                btnBM.setSelected(false);
                btnNSX.setSelected(false);
                btnTG.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 20sp
                btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 16sp
                btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
                btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
            }
        });

        btnBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAuthor.setVisibility(View.GONE);
                rcvBookC.setVisibility(View.GONE);
                rcvOther.setVisibility(View.GONE);
                rcvNSX.setVisibility(View.VISIBLE);

                btnTG.setSelected(false);
                btnBC.setSelected(false);
                btnBM.setSelected(true);
                btnNSX.setSelected(false);

                btnTG.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 20sp
                btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
                btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 16sp
                btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
            }
        });
        btnNSX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAuthor.setVisibility(View.GONE);
                rcvBookC.setVisibility(View.GONE);
                rcvOther.setVisibility(View.VISIBLE);
                rcvNSX.setVisibility(View.GONE);

                btnTG.setSelected(false);
                btnBC.setSelected(false);
                btnBM.setSelected(false);
                btnNSX.setSelected(true);

                btnTG.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 20sp
                btnBC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
                btnBM.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // 16sp
                btnNSX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 16sp
            }
        });
    }

    private List<BookB> getListBookM() {


        List<BookB> list = new ArrayList<>();
        list.add(new BookB(R.drawable.book1, "book 1 ", "500$"));
        list.add(new BookB(R.drawable.book2, "book 2 ", "500$"));
        list.add(new BookB(R.drawable.book3, "book 3 ", "500$"));
        list.add(new BookB(R.drawable.book4, "book 4 ", "500$"));
        list.add(new BookB(R.drawable.book5, "book 5 ", "500$"));
        return list;
    }


    // Danh sách tác giả
    private List<Author> getListAuthor() {
        List<Author> list = new ArrayList<>();
        list.add(new Author(R.drawable.book2, "Author1 ", 30));
        list.add(new Author(R.drawable.bell, "Author2 ", 18));
        list.add(new Author(R.drawable.bell, "Author3 ", 18));
        list.add(new Author(R.drawable.bell, "Author4 ", 18));
        list.add(new Author(R.drawable.bell, "Author5 ", 18));
        return list;
    }

    // Danh sách sách bìa cứng
    private List<BookB> getListBookN() {
        List<BookB> list = new ArrayList<>();
        list.add(new BookB(R.drawable.book1, "book 1 ", "500$"));
        list.add(new BookB(R.drawable.book2, "book 2 ", "500$"));
        list.add(new BookB(R.drawable.book3, "book 3 ", "500$"));
        list.add(new BookB(R.drawable.book4, "book 4 ", "500$"));
        list.add(new BookB(R.drawable.book5, "book 5 ", "500$"));
        return list;
    }

    // Danh sách sách khác
    private List<Author> getListOther() {
        List<Author> list = new ArrayList<>();
        list.add(new Author(R.drawable.book2, "Author1 ", 19008088));
        list.add(new Author(R.drawable.bell, "Author2 ", 19008088));
        list.add(new Author(R.drawable.bell, "Author3 ", 19008088));
        list.add(new Author(R.drawable.bell, "Author4 ", 19008088));
        list.add(new Author(R.drawable.bell, "Author5 ", 19008088));
        return list;
    }

}
