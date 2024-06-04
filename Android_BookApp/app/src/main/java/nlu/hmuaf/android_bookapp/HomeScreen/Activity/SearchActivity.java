package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.AuthorAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.BookAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OtherAdapter;
import nlu.hmuaf.android_bookapp.R;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sreach_activity);  // Sử dụng layout activity_search.xml

        // RecyclerView cho AuthorRecyclerView
        RecyclerView authorRecyclerView = findViewById(R.id.AuthorRecyclerView);
        LinearLayoutManager authorLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        authorRecyclerView.setLayoutManager(authorLayoutManager);

        // Danh sách các ID hình ảnh từ thư mục drawable cho author_item
        List<Integer> authorImageIds = new ArrayList<>();
        authorImageIds.add(R.drawable.bell);
        authorImageIds.add(R.drawable.book);
        authorImageIds.add(R.drawable.home);
        authorImageIds.add(R.drawable.search);
        // Thêm các ID hình ảnh khác nếu cần

        // Danh sách các văn bản cho author_item
        List<String> authorNames = new ArrayList<>();
        authorNames.add("Author 1");
        authorNames.add("Author 2");
        authorNames.add("Author 3");
        authorNames.add("Author 4");
        // Thêm các văn bản khác nếu cần

        // Tạo một Adapter và thiết lập cho RecyclerView
        AuthorAdapter authorAdapter = new AuthorAdapter(this, authorImageIds, authorNames);
        authorRecyclerView.setAdapter(authorAdapter);

        // RecyclerView cho BookNameRecyclerView
        RecyclerView bookRecyclerView = findViewById(R.id.BookNameRecyclerView);
        LinearLayoutManager bookLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bookRecyclerView.setLayoutManager(bookLayoutManager);

        // Danh sách các ID hình ảnh từ thư mục drawable cho bookname_item
        List<Integer> bookImageIds = new ArrayList<>();
        bookImageIds.add(R.drawable.book);
        bookImageIds.add(R.drawable.border_background);
        bookImageIds.add(R.drawable.profile);
        bookImageIds.add(R.drawable.library);
        // Thêm các ID hình ảnh khác nếu cần

        // Danh sách các văn bản cho bookname_item
        List<String> bookNames = new ArrayList<>();
        bookNames.add("Book 1");
        bookNames.add("Book 2");
        bookNames.add("Book 3");
        bookNames.add("Book 4");
        // Thêm các văn bản khác nếu cần

        // Tạo một Adapter và thiết lập cho RecyclerView
        BookAdapter bookAdapter = new BookAdapter(this, bookImageIds, bookNames);
        bookRecyclerView.setAdapter(bookAdapter);

        // RecyclerView cho OtherRecyclerView
        RecyclerView otherRecyclerView = findViewById(R.id.OtherRecyclerView);
        LinearLayoutManager otherLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        otherRecyclerView.setLayoutManager(otherLayoutManager);

        // Danh sách các ID hình ảnh từ thư mục drawable cho other_item
        List<Integer> otherImageIds = new ArrayList<>();
        otherImageIds.add(R.drawable.bell);
        otherImageIds.add(R.drawable.book);
        // Thêm các ID hình ảnh khác nếu cần

        // Danh sách các văn bản cho other_item
        List<String> otherNames = new ArrayList<>();
        otherNames.add("Other 1");
        otherNames.add("Other 2");
        // Thêm các văn bản khác nếu cần

        // Tạo một Adapter và thiết lập cho RecyclerView
        OtherAdapter otherAdapter = new OtherAdapter(this, otherImageIds, otherNames);
        otherRecyclerView.setAdapter(otherAdapter);

        // Tìm và thêm sự kiện onClick cho chuyển đến HomeActivity
        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến LibraryActivity
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

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
}
