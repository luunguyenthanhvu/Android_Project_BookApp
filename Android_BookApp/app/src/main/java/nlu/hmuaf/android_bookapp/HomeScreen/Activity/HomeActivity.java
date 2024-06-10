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

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.ImageAdapter;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.PopularAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.activity.LogOutActivity;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rcv1Data;
    private RecyclerView rcv2Data;
    private ImageAdapter imageAdapter;
    private PopularAdapter popularAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        rcv1Data = findViewById(R.id.rcv1_Data);
        rcv2Data = findViewById(R.id.rcv2_Data);
        // Tìm TextView theo ID
        TextView tvPrevious = findViewById(R.id.tv_previous);
        TextView textViewViewMore = findViewById(R.id.textViewViewMore);
        textViewViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang FantasyActivity khi click vào chữ "viewMore"
                Intent intent = new Intent(HomeActivity.this, FantasyActivity.class);
                startActivity(intent);
            }
        });
        TextView textViewViewMore2 = findViewById(R.id.textViewViewMore2);
        textViewViewMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang FantasyActivity khi click vào chữ "viewMore"
                Intent intent = new Intent(HomeActivity.this, FantasyActivity.class);
                startActivity(intent);
            }
        });
        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing or perform another action
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv1Data.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv2Data.setLayoutManager(layoutManager2);

        imageAdapter = new ImageAdapter(getListBookB2(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcv1Data.setAdapter(imageAdapter);

        popularAdapter = new PopularAdapter(getListBookB(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                intent.putExtra("book_position", position);
                startActivity(intent);
            }
        });
        rcv2Data.setAdapter(popularAdapter);

        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        // Set click listeners for footer
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(HomeActivity.this, LogOutActivity.class);
                startActivity(intent);
            }
        });
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchActivity
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<BookB> getListBookB2() {
        List<BookB>  list = new ArrayList<>();
        list.add(new BookB(R.drawable.book1,"book 1 " , "500$"));
        list.add(new BookB(R.drawable.book2,"book 2 " , "500$"));
        list.add(new BookB(R.drawable.book3,"book 3 " , "500$"));
        list.add(new BookB(R.drawable.book4,"book 4 " , "500$"));
        list.add(new BookB(R.drawable.book5,"book 5 " , "500$"));
        return list;
    }

    private List<BookB> getListBookB() {
         List<BookB>  list = new ArrayList<>();
        list.add(new BookB(R.drawable.book1,"book 1 " , "500$"));
        list.add(new BookB(R.drawable.book2,"book 2 " , "500$"));
        list.add(new BookB(R.drawable.book3,"book 3 " , "500$"));
        list.add(new BookB(R.drawable.book4,"book 4 " , "500$"));
        list.add(new BookB(R.drawable.book5,"book 5 " , "500$"));
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




