package nlu.hmuaf.android_bookapp.HomeScreen.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.FantasyAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class FantasyActivity extends AppCompatActivity implements FantasyAdapter.OnItemClickListener {

    private EditText editText;
    private ImageView imageView;
    private RecyclerView fantasyRecyclerView;
    private FantasyAdapter fantasyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fantasy_activity);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        fantasyRecyclerView = findViewById(R.id.FantasyRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        fantasyRecyclerView.setLayoutManager(layoutManager);

        fantasyAdapter = new FantasyAdapter(getListFantasy(), this);
        fantasyRecyclerView.setAdapter(fantasyAdapter);
        TextView tvPrevious = findViewById(R.id.tv_previous);

        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing or perform another action
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(editText.getText().toString());
            }
        });

        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<BookB> getListFantasy() {
        List<BookB> list = new ArrayList<>();
        list.add(new BookB(R.drawable.bell, "book 1 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 2 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 3 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 4 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 5 ", "500$"));
        return list;
    }

    private void performSearch(String query) {
        Intent intent = new Intent(FantasyActivity.this, LibraryActivity.class);
        intent.putExtra("search_query", query);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(FantasyActivity.this, BookActivity.class);
        intent.putExtra("book_position", position);
        startActivity(intent);
    }

    // Thêm phương thức để xử lý sự kiện khi người dùng nhấn vào hình chuông
    public void onBellClick(int position) {
        BookB clickedBook = getListFantasy().get(position);
        Intent intent = new Intent(FantasyActivity.this, LibraryActivity.class);
        intent.putExtra("clicked_book", clickedBook);
        startActivity(intent);
    }
}
