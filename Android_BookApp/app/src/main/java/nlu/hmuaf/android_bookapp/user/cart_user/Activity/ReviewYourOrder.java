package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewBookChosenAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewBookForMyCartAdapter;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.home.activity.BookActivity;
import nlu.hmuaf.android_bookapp.user.home.adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class ReviewYourOrder extends AppCompatActivity {
    private StepsView stepView;
    private Toolbar toolbar;

    private RecyclerView recycleListBookChosen;
    private List<CartItems> listBook = new ArrayList<>();

    private Button btn_continue;
    private CartService cartService;

    private RecycleViewBookChosenAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_your_order);
        stepView = findViewById(R.id.stepViewInReviewYourOrder);
        toolbar = findViewById(R.id.toolbar2);
        recycleListBookChosen = findViewById(R.id.listBookChosen);
        cartService = new CartService(getApplicationContext());
        btn_continue = findViewById(R.id.button3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] arrayString = {"Bước 1", "Bước 2", "Bước 3", "Bước 4"};
        stepView.setLabels(arrayString) // Đặt nhãn cho StepsView
                .setBarColorIndicator(Color.parseColor("#E8E4E9")) // Đặt màu mặc định cho thanh chỉ báo (màu xám)
                .setProgressColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho chỉ báo tiến độ (màu xám)
                .setLabelColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho nhãn (màu xám)
                .setCompletedPosition(0) // Đặt vị trí đã hoàn thành
                .drawView(); // Vẽ StepsView

        adapter2 = new RecycleViewBookChosenAdapter(this, listBook);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleListBookChosen.setLayoutManager(linearLayoutManager2);
        recycleListBookChosen.setAdapter(adapter2);
        List<Long> itemChoose = (List<Long>) getIntent().getSerializableExtra("listBookChoose");
        getCartItemData(itemChoose);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewYourOrder.this, DeliveryAddress.class);
                intent.putExtra("listBookChoose", (ArrayList<CartItems>) listBook);
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

    private void getCartItemData(List<Long> itemId) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            listBook = cartService.getItemUserChosen(MyUtils.getTokenResponse(getApplicationContext()).getUserId(), itemId);

            runOnUiThread(() -> {
                adapter2.updateData(listBook);
            });
        });
    }

}