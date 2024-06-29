package nlu.hmuaf.android_bookapp.admin.manage_inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import nlu.hmuaf.android_bookapp.R;

public class ManageInventoryActivity extends AppCompatActivity {

    private TextView tvInStock, tvInStockCount, tvHiddenProducts, tvHiddenProductsCount, tvOutOfStock, tvOutOfStockCount, tvLowStock, tvLowStockCount, tvSKU, tvSKUCount;
    private Button btnBulkUpdate;
    private List<Book> allBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_manage_inventory);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lý Tồn Kho");

        tvInStock = findViewById(R.id.tvInStock);
        tvInStockCount = findViewById(R.id.tvInStockCount);
        tvHiddenProducts = findViewById(R.id.tvHiddenProducts);
        tvHiddenProductsCount = findViewById(R.id.tvHiddenProductsCount);
        tvOutOfStock = findViewById(R.id.tvOutOfStock);
        tvOutOfStockCount = findViewById(R.id.tvOutOfStockCount);
        tvLowStock = findViewById(R.id.tvLowStock);
        tvLowStockCount = findViewById(R.id.tvLowStockCount);
        tvSKU = findViewById(R.id.tvSKU);
        tvSKUCount = findViewById(R.id.tvSKUCount);
        btnBulkUpdate = findViewById(R.id.btnBulkUpdate);

        // Fetch all books
        allBooks = getAllBooks();

        // Update counts
        updateCounts();

        tvInStock.setOnClickListener(v -> startDetailActivity(1));
        tvHiddenProducts.setOnClickListener(v -> startDetailActivity(2));
        tvOutOfStock.setOnClickListener(v -> startDetailActivity(3));
        tvLowStock.setOnClickListener(v -> startDetailActivity(4));
        tvSKU.setOnClickListener(v -> startDetailActivity(5));

        btnBulkUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, BulkUpdateActivity.class);
            startActivity(intent);
        });
    }

    private void startDetailActivity(int tabIndex) {
        Intent intent = new Intent(ManageInventoryActivity.this, ManageInventorDetailActivity.class);
        intent.putExtra("tabIndex", tabIndex);
        intent.putExtra("allBooks", (ArrayList<Book>) allBooks);
        startActivity(intent);
    }

    private void updateCounts() {
        tvInStockCount.setText("(" + countInStock() + ")");
        tvHiddenProductsCount.setText("(" + countHidden() + ")");
        tvOutOfStockCount.setText("(" + countOutOfStock() + ")");
        tvLowStockCount.setText("(" + countLowStock() + ")");
        tvSKUCount.setText("(" + countPreOrder() + ")");
    }

    private int countInStock() {
        int count = 0;
        for (Book book : allBooks) {
            if ("In Stock".equals(book.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countHidden() {
        int count = 0;
        for (Book book : allBooks) {
            if ("Hidden".equals(book.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countOutOfStock() {
        int count = 0;
        for (Book book : allBooks) {
            if ("Out of Stock".equals(book.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countLowStock() {
        int count = 0;
        for (Book book : allBooks) {
            if ("Low Stock".equals(book.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countPreOrder() {
        int count = 0;
        for (Book book : allBooks) {
            if (book.isPreOrder()) {
                count++;
            }
        }
        return count;
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
        book1.setStatus("In Stock");

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
        book2.setStatus("Hidden");

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
        book3.setStatus("Out of Stock");

        Book book4 = new Book();
        book4.setBookID("B4");
        book4.setTitle("1984");
        book4.setDescription("A dystopian social science fiction novel and cautionary tale by th    e English writer George Orwell.");
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
        book4.setStatus("Out of Stock");

        // Add SKU product
        Book book5 = new Book();
        book5.setBookID("B5");
        book5.setTitle("Pre-Order Book");
        book5.setDescription("A book available for pre-order.");
        book5.setPrice(49.99);
        book5.setNumberOfPages(350);
        book5.setPublicationDate("2024-12-01");
        book5.setLanguage("English");
        book5.setGenre("Science Fiction");
        book5.setPublisherName("Future Publisher");
        book5.setAuthor("Future Author");
        book5.setDiscountCode("PRE20");
        book5.setQuantity(0);
        book5.setImageUrl("avatar");
        book5.setStatus("Pre Order");
        book5.setPreOrder(true);

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
