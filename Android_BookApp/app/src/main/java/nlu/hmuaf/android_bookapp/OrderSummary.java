package nlu.hmuaf.android_bookapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class OrderSummary extends AppCompatActivity {
    private Toolbar toolbar;
    private StepView stepView;
    private List<String> listStepView = new ArrayList<>();
    private RecyclerView recyclerView;
     private TextView address;

     private TextView paymentMethod;
     private ImageButton changePaymentMethod;
     private TextView priceDetail;
     private TextView price;
     private Button placeOrder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);
        toolbar = findViewById(R.id.toolbarOrderSummary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stepView = findViewById(R.id.stepViewInOrderSummary);
        listStepView.add("Cart");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");
        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
        stepView.go(0, true);

        recyclerView = findViewById(R.id.recycleViewBook);
        address = findViewById(R.id.textViewAddress);
        paymentMethod = findViewById(R.id.textViewPaymentMethod);
        changePaymentMethod = findViewById(R.id.buttonChangePaymentMethod);
        priceDetail = findViewById(R.id.tv_priceDetails);
        price = findViewById(R.id.tv_price);
        placeOrder = findViewById(R.id.buttonPlaceOrder);

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
