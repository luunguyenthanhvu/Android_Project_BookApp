package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewBookChosenAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;

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
    private List<CartItems> listBook = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);
        toolbar = findViewById(R.id.toolbarOrderSummary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stepView = findViewById(R.id.stepViewInOrderSummary);
        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");

        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
        stepView.go(3, true);
        stepView.done(true);

        recyclerView = findViewById(R.id.recycleViewBook);
        address = findViewById(R.id.textViewAddress);
        paymentMethod = findViewById(R.id.textViewPaymentMethod);
        priceDetail = findViewById(R.id.tv_priceDetails);
        price = findViewById(R.id.tv_price);
        placeOrder = findViewById(R.id.buttonPlaceOrder);

        listBook = (ArrayList<CartItems>) getIntent().getSerializableExtra("listBook");
        HashMap<Integer, Integer> quantityMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("quantityMap");
        RecycleViewBookChosenAdapter adapter2 = new RecycleViewBookChosenAdapter(this, listBook, quantityMap);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setAdapter(adapter2);

        Address address1 = (Address) getIntent().getSerializableExtra("address");
        address.setText(address1.getStreet() + ", " + address1.getWard() + ", " + address1.getDistrict() + ", " + address1.getCity());
        paymentMethod.setText(getIntent().getStringExtra("paymentMethod"));
        priceDetail.setText("Price Details " + adapter2.countQuantity() + " items");
        price.setText("Total: " + adapter2.getTotalPrice() + " VNĐ");

        double totalPriceTemp = adapter2.getTotalPrice();
        placeOrder.setOnClickListener(v -> {
            Intent intent = new Intent(OrderSummary.this, OrderSuccessfully.class);
            intent.putExtra("totalPrice", totalPriceTemp);
            intent.putExtra("listBook", (ArrayList<CartItems>) listBook);
            intent.putExtra("quantityEachBook", quantityMap);
            startActivity(intent);
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
