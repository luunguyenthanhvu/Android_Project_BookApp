package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;

public class HiddenProductsActivity extends AppCompatActivity {

    private EditText etProductName;
    private Button btnSearch;
    private RecyclerView rvBookList;
    private BookAdapter adapter;
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_hidden_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Phân loại đã ẩn và đã bị khóa");

        etProductName = findViewById(R.id.etProductName);
        btnSearch = findViewById(R.id.btnSearch);
        rvBookList = findViewById(R.id.rvProductList);

        rvBookList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(books, HiddenProductsActivity.this);
        rvBookList.setAdapter(adapter);


        btnSearch.setOnClickListener(v -> performSearch(etProductName.getText().toString()));
    }

    private void handleBookClick(Book book) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("book", book);  // Make sure Book class implements Parcelable or Serializable
        startActivity(intent);
    }

    private void performSearch(String query) {
        // Fetch data based on query
        books.clear();
        books.addAll(fetchBooks(query)); // Assume this method returns a list of books
        adapter.notifyDataSetChanged();
    }

    private List<Book> fetchBooks(String query) {
        // Perform the search here and return the list of books
        List<Book> filteredBooks = new ArrayList<>();
        // Example book fetching logic
        for (Book book : getAllBooks()) {
            if (book.getBookID().toLowerCase().contains(query.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    private List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        // Add first sample book
        Book book1 = new Book();
        book1.setBookID("B1");
        book1.setTitle("To Kill a Mockingbird");
        book1.setDescription("A novel by Harper Lee published in 1960.");
        book1.setPrice(39.99);
        book1.setNumberOfPages(281);
        book1.setPublicationDate("1960-07-11");
        book1.setLanguage("English");
        book1.setGenre("Fiction");
        book1.setPublisherName("J. B. Lippincott & Co.");
        book1.setAuthor("Harper Lee");
        book1.setDiscountCode("DISC10");
        book1.setQuantity(150);
        book1.setImageUrl("avatar");

        // Add second sample book
        Book book2 = new Book();
        book2.setBookID("B2");
        book2.setTitle("1984");
        book2.setDescription("A dystopian social science fiction novel and cautionary tale by the English writer George Orwell.");
        book2.setPrice(29.99);
        book2.setNumberOfPages(328);
        book2.setPublicationDate("1949-06-08");
        book2.setLanguage("English");
        book2.setGenre("Dystopian, Political fiction, Social science fiction");
        book2.setPublisherName("Secker & Warburg");
        book2.setAuthor("George Orwell");
        book2.setDiscountCode("DISC20");
        book2.setQuantity(200);
        book2.setImageUrl("avatar");

        Book book3 = new Book();
        book3.setTitle("1984");
        book3.setBookID("B3");
        book3.setDescription("A dystopian social science fiction novel and cautionary tale by the English writer George Orwell.");
        book3.setPrice(29.99);
        book3.setNumberOfPages(328);
        book3.setPublicationDate("1949-06-08");
        book3.setLanguage("English");
        book3.setGenre("Dystopian, Political fiction, Social science fiction");
        book3.setPublisherName("Secker & Warburg");
        book3.setAuthor("George Orwell");
        book3.setDiscountCode("DISC20");
        book3.setQuantity(200);
        book3.setImageUrl("avatar");

        Book book4 = new Book();
        book4.setBookID("B4");
        book4.setTitle("1984");
        book4.setDescription("A dystopian social science fiction novel and cautionary tale by the English writer George Orwell.");
        book4.setPrice(29.99);
        book4.setNumberOfPages(328);
        book4.setPublicationDate("1949-06-08");
        book4.setLanguage("English");
        book4.setGenre("Dystopian, Political fiction, Social science fiction");
        book4.setPublisherName("Secker & Warburg");
        book4.setAuthor("George Orwell");
        book4.setDiscountCode("DISC20");
        book4.setQuantity(200);
        book4.setImageUrl("avatar");

        Book book5 = new Book();
        book5.setBookID("B5");
        book5.setTitle("1984");
        book5.setDescription("A dystopian social science fiction novel and cautionary tale by the English writer George Orwell.");
        book5.setPrice(29.99);
        book5.setNumberOfPages(328);
        book5.setPublicationDate("1949-06-08");
        book5.setLanguage("English");
        book5.setGenre("Dystopian, Political fiction, Social science fiction");
        book5.setPublisherName("Secker & Warburg");
        book5.setAuthor("George Orwell");
        book5.setDiscountCode("DISC20");
        book5.setQuantity(200);
        book5.setImageUrl("avatar");

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);

        return books;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
