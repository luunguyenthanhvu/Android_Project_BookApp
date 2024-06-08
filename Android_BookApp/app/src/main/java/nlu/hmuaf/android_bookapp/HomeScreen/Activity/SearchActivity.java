package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.AuthorAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.BookAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.ImageAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OtherAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.PopularAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.Author;

import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;


public class SearchActivity extends AppCompatActivity {
    private RecyclerView rcvAuthor;
    private RecyclerView rcvBook;
    private RecyclerView rcvOther;
    private AuthorAdapter authorAdapter;
    private BookAdapter bookAdapter;
    private OtherAdapter otherAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sreach_activity);  // Sử dụng layout activity_search.xml
        TextView tvPrevious = findViewById(R.id.tv_previous);
        rcvAuthor = findViewById(R.id.AuthorRecyclerView);
        rcvBook = findViewById(R.id.BookNameRecyclerView);
        rcvOther = findViewById(R.id.OtherRecyclerView);
        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kết thúc Activity hiện tại để quay lại trang trước
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvAuthor.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvBook.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvOther.setLayoutManager(layoutManager3);

        authorAdapter = new AuthorAdapter(getListAuthor(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcvAuthor.setAdapter(authorAdapter);

        bookAdapter= new BookAdapter(getListBookN(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcvBook.setAdapter(bookAdapter);

        otherAdapter= new OtherAdapter(getListOther(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, FantasyActivity.class);

                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcvOther.setAdapter(otherAdapter);

        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);

        // Set click listeners for footer
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchActivity
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(SearchActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Author> getListAuthor() {
        List<Author>  list = new ArrayList<>();
        list.add(new Author(R.drawable.bell,"Author1 " , 18));
        list.add(new Author(R.drawable.bell,"Author2 " , 18));
        list.add(new Author(R.drawable.bell,"Author3 " , 18));
        list.add(new Author(R.drawable.bell,"Author4 " , 18));
        list.add(new Author(R.drawable.bell,"Author5 " , 18));

        return list;
    }

    private List<BookB> getListBookN() {
        List<BookB>  list = new ArrayList<>();
        list.add(new BookB(R.drawable.bell,"book 1 " , "500$"));
        list.add(new BookB(R.drawable.book,"book 2 " , "500$"));
        list.add(new BookB(R.drawable.apple,"book 3 " , "500$"));
        list.add(new BookB(R.drawable.avatar,"book 4 " , "500$"));
        list.add(new BookB(R.drawable.cart,"book 5 " , "500$"));
        return list;
    }
    private List<BookB> getListOther() {
        List<BookB>  list = new ArrayList<>();
        list.add(new BookB(R.drawable.bell,"book 1 " , "500$"));
        list.add(new BookB(R.drawable.book,"book 2 " , "500$"));
        list.add(new BookB(R.drawable.apple,"book 3 " , "500$"));
        list.add(new BookB(R.drawable.avatar,"book 4 " , "500$"));
        list.add(new BookB(R.drawable.cart,"book 5 " , "500$"));
        return list;
    }

//        // Tìm và thêm sự kiện onClick cho chuyển đến ProfileActivity
//        LinearLayout profileLayout = findViewById(R.id.profileLayout);
//        profileLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
    }

