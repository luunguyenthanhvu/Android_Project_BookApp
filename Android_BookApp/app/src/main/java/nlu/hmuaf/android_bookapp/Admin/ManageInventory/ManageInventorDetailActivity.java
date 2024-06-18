package nlu.hmuaf.android_bookapp.Admin.ManageInventory;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;

public class ManageInventorDetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> allBooks;
    private EditText etProductName;
    private Spinner spinnerStorageLocation;
    private Button btnSearch, btnReset;
    private int tabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_manage_inventory_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tổng quan tồn kho");

        etProductName = findViewById(R.id.etProductName);
        spinnerStorageLocation = findViewById(R.id.spinnerStorageLocation);
        btnSearch = findViewById(R.id.btnSearch);
        btnReset = findViewById(R.id.btnReset);

        tabLayout = findViewById(R.id.tab_layout);
        recyclerView = findViewById(R.id.rvProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize spinner with locations array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStorageLocation.setAdapter(adapter);

        // Receive all books from intent
        allBooks = (List<Book>) getIntent().getSerializableExtra("allBooks");
        tabIndex = getIntent().getIntExtra("tabIndex", 0);

        // Set up adapter and recycler view
        bookAdapter = new BookAdapter(allBooks, ManageInventorDetailActivity.this);
        recyclerView.setAdapter(bookAdapter);

        // Set up tab layout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                List<Book> newBookList = getBooksForTab(position);
                bookAdapter.updateBooks(newBookList);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Add tabs
        tabLayout.addTab(tabLayout.newTab().setText("Tất cả (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Còn hàng (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã ẩn (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Hết hàng (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Sắp hết hàng (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("SKU đặt hàng (0)"));

        // Update tab titles with the number of books
        updateTabTitles();

        // Set selected tab based on tabIndex
        TabLayout.Tab tab = tabLayout.getTabAt(tabIndex);
        if (tab != null) {
            tab.select();
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(etProductName.getText().toString(), spinnerStorageLocation.getSelectedItem().toString());
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etProductName.setText("");
                spinnerStorageLocation.setSelection(0);
                performSearch("", "");
            }
        });
    }

    private void updateTabTitles() {
        tabLayout.getTabAt(0).setText("Tất cả (" + getBooksForTab(0).size() + ")");
        tabLayout.getTabAt(1).setText("Còn hàng (" + getBooksForTab(1).size() + ")");
        tabLayout.getTabAt(2).setText("Đã ẩn (" + getBooksForTab(2).size() + ")");
        tabLayout.getTabAt(3).setText("Hết hàng (" + getBooksForTab(3).size() + ")");
        tabLayout.getTabAt(4).setText("Sắp hết hàng (" + getBooksForTab(4).size() + ")");
        tabLayout.getTabAt(5).setText("SKU đặt hàng (" + getBooksForTab(5).size() + ")");
    }

    private List<Book> getBooksForTab(int tabPosition) {
        List<Book> books = new ArrayList<>();
        switch (tabPosition) {
            case 0:
                books.addAll(allBooks);
                break;
            case 1:
                for (Book book : allBooks) {
                    if ("In Stock".equals(book.getStatus())) {
                        books.add(book);
                    }
                }
                break;
            case 2:
                for (Book book : allBooks) {
                    if ("Hidden".equals(book.getStatus())) {
                        books.add(book);
                    }
                }
                break;
            case 3:
                for (Book book : allBooks) {
                    if ("Out of Stock".equals(book.getStatus())) {
                        books.add(book);
                    }
                }
                break;
            case 4:
                for (Book book : allBooks) {
                    if ("Low Stock".equals(book.getStatus())) {
                        books.add(book);
                    }
                }
                break;
            case 5:
                for (Book book : allBooks) {
                    if (book.isPreOrder()) {
                        books.add(book);
                    }
                }
                break;
        }
        return books;
    }

    private void performSearch(String productName, String storageLocation) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(productName.toLowerCase()) &&
                    book.getStorageLocation().toLowerCase().contains(storageLocation.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        bookAdapter.updateBooks(filteredBooks);
        updateTabTitles();
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
