package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.ImageAdapter;

import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rcv1Data;
    private ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        rcv1Data = findViewById(R.id.rcv_Data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv1Data.setLayoutManager(layoutManager);

        imageAdapter = new ImageAdapter(getListBookB());
        rcv1Data.setAdapter(imageAdapter);
    }
        private List<BookB> getListBookB() {
         List<BookB>  list = new ArrayList<>();
         list.add(new BookB(R.drawable.bell,"book 1 " , "500$"));
            list.add(new BookB(R.drawable.book_login,"book 2 " , "500$"));
            list.add(new BookB(R.drawable.book_login,"book 3 " , "500$"));
            list.add(new BookB(R.drawable.book_login,"book 4 " , "500$"));
            list.add(new BookB(R.drawable.book_login,"book 5 " , "500$"));
         return list;
        }

        // RecyclerView cho recycler_item
//        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        // Danh sách các ID hình ảnh từ thư mục drawable cho recycler_item
//        List<Integer> imageIds = new ArrayList<>();
//        imageIds.add(R.drawable.bell);
//        imageIds.add(R.drawable.library);
//        // Thêm các ID hình ảnh khác nếu cần
//        imageIds.add(R.drawable.home);
//
//        // Tạo một Adapter và thiết lập cho RecyclerView
//        ImageAdapter adapter;
//        adapter = new ImageAdapter(this, imageIds);
//        recyclerView.setAdapter(adapter);
//
//
//        // RecyclerView cho popular_item
//        RecyclerView recyclerViewPopular = findViewById(R.id.PopularRecyclerView);
//        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
//
//        // Danh sách các ID hình ảnh từ thư mục drawable cho popular_item
//        List<Integer> popularImageIds = new ArrayList<>();
//        popularImageIds.add(R.drawable.home);
//        popularImageIds.add(R.drawable.search);
//        // Thêm các ID hình ảnh khác nếu cần
//
//        // Danh sách các văn bản cho popular_item
//        List<String> popularTexts = new ArrayList<>();
//        popularTexts.add("Book 1");
//        popularTexts.add("Book 2");
//        // Thêm các văn bản khác nếu cần
//
//        // Tạo một Adapter và thiết lập cho RecyclerView
//        PopularAdapter popularAdapter = new PopularAdapter(this, popularImageIds, popularTexts);
//        recyclerViewPopular.setAdapter(popularAdapter);
//
//        // Tìm và thêm sự kiện onClick cho chuyển đến SearchActivity
//        LinearLayout searchLayout = findViewById(R.id.searchLayout);
//        searchLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Tìm và thêm sự kiện onClick cho chuyển đến LibraryActivity
//        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
//        libraryLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
//                startActivity(intent);
//            }
//        });
//
////        // Tìm và thêm sự kiện onClick cho chuyển đến ProfileActivity
////        LinearLayout profileLayout = findViewById(R.id.profileLayout);
////        profileLayout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
////                startActivity(intent);
////            }
////        });
   }




