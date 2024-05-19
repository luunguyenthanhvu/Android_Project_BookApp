package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
//        Button btn = findViewById(R.id.button2);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.class.);
//            }
//        });

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
    }

}
