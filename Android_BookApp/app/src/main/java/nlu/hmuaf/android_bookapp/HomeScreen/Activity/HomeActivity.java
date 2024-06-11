package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.ImageAdapter;

import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.HomeScreen.Adapter.PopularAdapter;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.json.response.PageListBookResponseJson;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.profile.activity.LogOutActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rcv1Data;
    private RecyclerView rcv2Data;
    private ImageAdapter imageAdapter;
    private PopularAdapter popularAdapter;
    private BookAppApi bookAppApi;
    private List<ListBookResponseDTO> newListBook, Book = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        rcv1Data = findViewById(R.id.rcv1_Data);
        rcv2Data = findViewById(R.id.rcv2_Data);
        progressBar = findViewById(R.id.progressBar);

        // get data from api
        getNewBookData();

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

        // get popular book
        popularAdapter = new PopularAdapter(newListBook, new OnItemClickListener() {
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

    // get new book in shipment
    public void getNewBookData() {
        progressBar.setVisibility(View.VISIBLE);
        bookAppApi = BookAppService.getClient();
        Call<List<ListBookResponseDTO>> call = bookAppApi.getNewListBooks(0, 50);
        call.enqueue(new Callback<List<ListBookResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListBookResponseDTO>> call, Response<List<ListBookResponseDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    // Lấy danh sách sách mới từ phản hồi
                    newListBook = (List<ListBookResponseDTO>) response.body();
                    // Cập nhật RecyclerView với danh sách sách mới
                    updateNewBookRecyclerView(newListBook);
                } else {
                    // Xử lý lỗi khi không thành công
                    System.out.println("lỗi");
                }
            }

            @Override
            public void onFailure(Call<List<ListBookResponseDTO>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                // Xử lý lỗi khi gọi API thất bại
                System.out.println(throwable.getMessage());
            }
        });
    }

    // Phương thức này sẽ cập nhật RecyclerView với danh sách sách mới
    private void updateNewBookRecyclerView(List<ListBookResponseDTO> newListBooks) {
        if (popularAdapter == null) {
            // Khởi tạo adapter nếu chưa có
            popularAdapter = new PopularAdapter(newListBooks, new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Xử lý sự kiện khi người dùng nhấn vào một sách
                    Intent intent = new Intent(HomeActivity.this, BookActivity.class);
                    intent.putExtra("book_position", position);
                    startActivity(intent);
                }
            });
            rcv2Data.setAdapter(popularAdapter);
        } else {
            // Cập nhật dữ liệu mới cho adapter và thông báo thay đổi
            popularAdapter.updateData(newListBooks);
        }
    }


    private List<BookB> getListBookB2() {
        List<BookB> list = new ArrayList<>();
        list.add(new BookB(R.drawable.book1, "book 1 ", "500$"));
        list.add(new BookB(R.drawable.book2, "book 2 ", "500$"));
        list.add(new BookB(R.drawable.book3, "book 3 ", "500$"));
        list.add(new BookB(R.drawable.book4, "book 4 ", "500$"));
        list.add(new BookB(R.drawable.book5, "book 5 ", "500$"));
        return list;
    }
}




