package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anton46.stepsview.StepsView;
//import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewBookChosenAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewBookForMyCartAdapter;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.home.activity.BookActivity;
import nlu.hmuaf.android_bookapp.user.home.adapter.OnItemClickListener;

public class ReviewYourOrder extends AppCompatActivity {
    private StepsView stepView;
    private List<String> listStepView = new ArrayList<>();
    private Toolbar toolbar;

    private RecyclerView recycleListBookChosen;
    private List<CartItems> listBook = new ArrayList<>();

    private Button btn_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_your_order);
        stepView = findViewById(R.id.stepViewInReviewYourOrder);
        toolbar = findViewById(R.id.toolbar2);
        recycleListBookChosen = findViewById(R.id.listBookChosen);

        listBook = (ArrayList<CartItems>) getIntent().getSerializableExtra("listBookChoose");

        RecycleViewBookForMyCartAdapter adapter = new RecycleViewBookForMyCartAdapter(this, listBook, null, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CartItems selectedBook = listBook.get(position);
                Intent intent = new Intent(ReviewYourOrder.this, BookActivity.class);
                intent.putExtra("BOOK_ID", selectedBook.getBookId());
                startActivity(intent);
            }
        });
        recycleListBookChosen.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleListBookChosen.setLayoutManager(linearLayoutManager);
        recycleListBookChosen.setAdapter(adapter);


        btn_continue = findViewById(R.id.button3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
//        stepView.go(0, true);
//        String[] arrayString = (String[]) listStepView.toArray();
        stepView.setLabels(new String[]{"1","2","3","4"}) // Đặt các bước (labels) cho StepsView
                .setBarColorIndicator(getApplicationContext().getColor(android.R.color.darker_gray)) // Đặt màu của thanh chỉ báo
                .setProgressColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo tiến độ
                .setLabelColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo nhãn
                .setCompletedPosition(0) // Đặt vị trí đã hoàn thành (nếu cần)
                .drawView(); // Vẽ StepsView

        listBook = (ArrayList<CartItems>) getIntent().getSerializableExtra("listBookChoose");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        RecycleViewBookChosenAdapter adapter2 = new RecycleViewBookChosenAdapter(this, listBook, quantityMap);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleListBookChosen.setLayoutManager(linearLayoutManager2);
        recycleListBookChosen.setAdapter(adapter2);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewYourOrder.this, DeliveryAddress.class);
                intent.putExtra("listBookChoose", (ArrayList<CartItems>) listBook);
                intent.putExtra("selectedQuantities", quantityMap);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Kết thúc activity hiện tại và quay lại màn hình trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
