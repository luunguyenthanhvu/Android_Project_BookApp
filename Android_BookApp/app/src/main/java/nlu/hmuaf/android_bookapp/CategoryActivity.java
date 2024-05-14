package nlu.hmuaf.android_bookapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        List<Book> books = new ArrayList<Book>();
        books.add(new Book("Tên sách 1 ", "Tác giả 1"));
        books.add(new Book("Tên sách 2 ", "Tác giả 1"));
        books.add(new Book("Tên sách 3 ", "Tác giả 1"));

        recyclerView = findViewById(R.id.bookList);
        LinearLayoutManager layoutMangager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutMangager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new BookAdapter(books, this));

    }
}
