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

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.ImageAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.PopularAdapter;
import nlu.hmuaf.android_bookapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // RecyclerView cho recycler_item
        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Danh sách các ID hình ảnh từ thư mục drawable cho recycler_item
        List<Integer> imageIds = new ArrayList<>();
        imageIds.add(R.drawable.bell);
        imageIds.add(R.drawable.library);
        // Thêm các ID hình ảnh khác nếu cần
        imageIds.add(R.drawable.home);

        // Tạo một Adapter và thiết lập cho RecyclerView
        ImageAdapter adapter = new ImageAdapter(this, imageIds);
        recyclerView.setAdapter(adapter);


        // RecyclerView cho popular_item
        RecyclerView recyclerViewPopular = findViewById(R.id.PopularRecyclerView);
        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);

        // Danh sách các ID hình ảnh từ thư mục drawable cho popular_item
        List<Integer> popularImageIds = new ArrayList<>();
        popularImageIds.add(R.drawable.home);
        popularImageIds.add(R.drawable.search);
        // Thêm các ID hình ảnh khác nếu cần

        // Danh sách các văn bản cho popular_item
        List<String> popularTexts = new ArrayList<>();
        popularTexts.add("Book 1");
        popularTexts.add("Book 2");
        // Thêm các văn bản khác nếu cần

        // Tạo một Adapter và thiết lập cho RecyclerView
        PopularAdapter popularAdapter = new PopularAdapter(this, popularImageIds, popularTexts);
        recyclerViewPopular.setAdapter(popularAdapter);

        // Tìm và thêm sự kiện onClick cho chuyển đến SearchActivity
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến LibraryActivity
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

//        // Tìm và thêm sự kiện onClick cho chuyển đến ProfileActivity
//        LinearLayout profileLayout = findViewById(R.id.profileLayout);
//        profileLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
   }
}

