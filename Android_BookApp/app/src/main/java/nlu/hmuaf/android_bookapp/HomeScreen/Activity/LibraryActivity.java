package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.LibraryAdapter;
import nlu.hmuaf.android_bookapp.R;

public class LibraryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLibrary;
    private LibraryAdapter libraryAdapter;
    private ArrayList<String> clickedItems;
    private ArrayList<Integer> bookImageIds; // Thêm biến này để lưu danh sách ID hình ảnh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);

        recyclerViewLibrary = findViewById(R.id.RecyclerViewLibrary);
        recyclerViewLibrary.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve the saved clicked items
        clickedItems = new ArrayList<>(getSavedClickedItems());

        // Tạo danh sách ID hình ảnh sách (giả sử bạn đã có các ID hình ảnh này)
        bookImageIds = new ArrayList<>();
        bookImageIds.add(R.drawable.book); // Thay thế bằng các ID hình ảnh thực tế của bạn
        bookImageIds.add(R.drawable.bell);
        // Thêm các ID hình ảnh khác tương ứng với danh sách sách

        // Truyền Context, clickedItems và bookImageIds vào constructor của LibraryAdapter
        libraryAdapter = new LibraryAdapter(this, clickedItems, bookImageIds);
        recyclerViewLibrary.setAdapter(libraryAdapter);

        // Tìm và thêm sự kiện onClick cho chuyển đến HomeActivity
        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến SearchActivity
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến ProfileActivity
//        LinearLayout profileLayout = findViewById(R.id.profileLayout);
//        profileLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LibraryActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
   }

    private Set<String> getSavedClickedItems() {
        Set<String> defaultSet = new HashSet<>();
        return getSharedPreferences("ClickedItemsPrefs", MODE_PRIVATE)
                .getStringSet("clickedItems", defaultSet);
    }
}
