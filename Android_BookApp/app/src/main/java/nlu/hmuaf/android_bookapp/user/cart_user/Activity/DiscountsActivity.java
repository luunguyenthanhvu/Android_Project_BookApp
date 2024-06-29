package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anton46.stepsview.StepsView;
//import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.FragmentListDiscountUser;
import nlu.hmuaf.android_bookapp.R;

public class DiscountsActivity extends AppCompatActivity {
    private StepsView stepView;
    private List<String> listStepView = new ArrayList<>();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discounts_activity);

        toolbar = findViewById(R.id.toolbarDiscountActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listStepView.add("Cart");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");

//        stepView = findViewById(R.id.stepViewInDiscountActivity);
//        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
//        stepView.go(0, true);
        stepView.setLabels((String[]) listStepView.toArray()) // Đặt các bước (labels) cho StepsView từ listStepView
                .setBarColorIndicator(getApplicationContext().getColor(android.R.color.darker_gray)) // Đặt màu của thanh chỉ báo
                .setProgressColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo tiến độ
                .setLabelColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo nhãn
                .setCompletedPosition(3) // Đặt vị trí đã hoàn thành (nếu cần, ví dụ: 3)
                .drawView(); // Vẽ StepsView


        // Tạo instance của Fragment
        FragmentListDiscountUser fragmentDiscountsUser = new FragmentListDiscountUser();

        // Lấy FragmentManager và bắt đầu transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thêm Fragment vào FrameLayout
        fragmentTransaction.add(R.id.fragment_container, fragmentDiscountsUser);
        fragmentTransaction.commit();
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
