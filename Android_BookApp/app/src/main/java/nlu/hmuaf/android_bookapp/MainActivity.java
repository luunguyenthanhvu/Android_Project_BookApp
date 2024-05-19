package nlu.hmuaf.android_bookapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView);

        // Danh sách các ID hình ảnh từ thư mục drawable
        List<Integer> imageIds = new ArrayList<>();
        imageIds.add(R.drawable.bell);
        imageIds.add(R.drawable.library);
        // Thêm các ID hình ảnh khác nếu cần
        imageIds.add(R.drawable.home);
        // Tạo một Adapter và thiết lập cho RecyclerView
        ImageAdapter adapter = new ImageAdapter(this, imageIds);
        recyclerView.setAdapter(adapter);

        // Thiết lập layout manager để hiển thị các item theo chiều ngang
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
