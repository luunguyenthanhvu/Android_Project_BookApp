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

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.LibraryAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class LibraryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLibrary;
    private LibraryAdapter libraryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);

        recyclerViewLibrary = findViewById(R.id.RecyclerViewLibrary);
        recyclerViewLibrary.setLayoutManager(new LinearLayoutManager(this));

        libraryAdapter = new LibraryAdapter(new ArrayList<>());
        recyclerViewLibrary.setAdapter(libraryAdapter);
        TextView tvPrevious = findViewById(R.id.tv_previous);

        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing or perform another action
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("clicked_book")) {
            BookB clickedBook = intent.getParcelableExtra("clicked_book");
            libraryAdapter.addBook(clickedBook);
        }

        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
