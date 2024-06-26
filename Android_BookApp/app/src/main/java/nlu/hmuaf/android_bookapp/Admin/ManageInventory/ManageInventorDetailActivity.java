package nlu.hmuaf.android_bookapp.admin.ManageInventory;

import android.os.Bundle;
import android.view.MenuItem;

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
    private int tabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_manage_inventory_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tổng quan tồn kho");

        tabLayout = findViewById(R.id.tab_layout);
        recyclerView = findViewById(R.id.rvProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allBooks = (List<Book>) getIntent().getSerializableExtra("allBooks");
        tabIndex = getIntent().getIntExtra("tabIndex", 0);
        bookAdapter = new BookAdapter(allBooks, ManageInventorDetailActivity.this);
        recyclerView.setAdapter(bookAdapter);

        setupTabs();
        selectInitialTab();
    }

    private void setupTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<Book> newBookList = getBooksForTab(tab.getPosition());
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

        updateTabTitles();
    }

    private void selectInitialTab() {
        TabLayout.Tab tab = tabLayout.getTabAt(tabIndex);
        if (tab != null) {
            tab.select();
        }
    }

    private void updateTabTitles() {
        if (tabLayout.getTabAt(0) != null) // For "Tất cả"
            tabLayout.getTabAt(0).setText("Tất cả (" + allBooks.size() + ")");
        if (tabLayout.getTabAt(1) != null) // For "Còn hàng"
            tabLayout.getTabAt(1).setText("Còn hàng (" + getBooksForStatus("In Stock").size() + ")");
        if (tabLayout.getTabAt(2) != null) // For "Đã ẩn"
            tabLayout.getTabAt(2).setText("Đã ẩn (" + getBooksForStatus("Hidden").size() + ")");
        if (tabLayout.getTabAt(3) != null) // For "Hết hàng"
            tabLayout.getTabAt(3).setText("Hết hàng (" + getBooksForStatus("Out of Stock").size() + ")");
        if (tabLayout.getTabAt(4) != null) // For "Sắp hết hàng"
            tabLayout.getTabAt(4).setText("Sắp hết hàng (" + getBooksForStatus("Low Stock").size() + ")");
        if (tabLayout.getTabAt(5) != null) // For "SKU đặt hàng"
            tabLayout.getTabAt(5).setText("SKU đặt hàng (" + getBooksForPreOrder().size() + ")");
    }

    private List<Book> getBooksForStatus(String status) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (status.equals(book.getStatus())) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    private List<Book> getBooksForPreOrder() {
        List<Book> preOrderBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.isPreOrder()) {
                preOrderBooks.add(book);
            }
        }
        return preOrderBooks;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
