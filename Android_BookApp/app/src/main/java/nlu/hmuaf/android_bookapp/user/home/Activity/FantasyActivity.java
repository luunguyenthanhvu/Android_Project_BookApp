package nlu.hmuaf.android_bookapp.user.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.home.adapter.FantasyAdapter;
import nlu.hmuaf.android_bookapp.user.home.classess.BookB;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;

public class FantasyActivity extends AppCompatActivity implements FantasyAdapter.OnItemClickListener {

    private EditText editText;
    private ImageView imageView;
    private RecyclerView fantasyRecyclerView;
    private FantasyAdapter fantasyAdapter;
    private int currentPage = 0; // Trang hiện tại
    private TextView tvPageNumber;
    private Button btnPrevious;
    private Button btnNext;
    private static final int ITEMS_PER_PAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fantasy_activity);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        fantasyRecyclerView = findViewById(R.id.FantasyRecyclerView);
        tvPageNumber = findViewById(R.id.tvPageNumber2);
        btnPrevious = findViewById(R.id.btnPrevious2);
        btnNext = findViewById(R.id.btnNext2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        fantasyRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(fantasyRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        fantasyRecyclerView.addItemDecoration(dividerItemDecoration);

        fantasyAdapter = new FantasyAdapter(new ArrayList<>(), this);
        fantasyRecyclerView.setAdapter(fantasyAdapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(editText.getText().toString());
            }
        });

        // Set click listeners for footer
        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);

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

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, LogOutActivity.class);
                startActivity(intent);
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
                int totalItems = getListFantasy().size();
                if ((currentPage + 1) * ITEMS_PER_PAGE < totalItems) {
                    currentPage++;
                    updateRecyclerView();
                }
            }
        });

        updateRecyclerView();
    }

    private void updateRecyclerView() {
        int totalItems = getListFantasy().size();
        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min((currentPage + 1) * ITEMS_PER_PAGE, totalItems);
        List<BookB> displayList = getListFantasy().subList(startIndex, endIndex);
        fantasyAdapter.updateList(displayList);
        int pageNumber = currentPage + 1;
        tvPageNumber.setText(String.valueOf(pageNumber));
    }

    private List<BookB> getListFantasy() {
        List<BookB> list = new ArrayList<>();
        list.add(new BookB(R.drawable.bell, "book 1 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 2 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 3 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 4 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 5 ", "500$"));
        list.add(new BookB(R.drawable.bell, "book 1 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 2 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 3 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 4 ", "500$"));
        list.add(new BookB(R.drawable.book_login, "book 5 ", "500$"));
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
}
