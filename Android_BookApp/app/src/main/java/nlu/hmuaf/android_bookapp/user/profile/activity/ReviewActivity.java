package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.user.profile.classess.Review;
import nlu.hmuaf.android_bookapp.user.profile.adapter.ReviewAdapter;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_review);
        DarkModeUtil.applyDarkMode(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviews");

        // Thêm nút quay lại trên Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.reviews_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu kiểm thử
        reviewList = new ArrayList<>();
        reviewList.add(new Review("Great Book", "I loved the storyline and the characters.", "John Doe", "The Great Gatsby"));
        reviewList.add(new Review("Not Bad", "The book was okay, but could be better.", "Jane Smith", "To Kill a Mockingbird"));
        reviewList.add(new Review("Must Read", "A must-read for anyone who loves mystery.", "Alice Johnson", "The Da Vinci Code"));
        // Thêm nhiều dữ liệu hơn nếu cần

        // Thiết lập adapter
        adapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi người dùng nhấn vào nút quay lại trên Toolbar
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
