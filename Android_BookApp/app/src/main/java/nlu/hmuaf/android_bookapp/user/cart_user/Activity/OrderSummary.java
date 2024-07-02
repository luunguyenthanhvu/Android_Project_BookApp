package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anton46.stepsview.StepsView;
//import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.admin.Home;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.user.bill.activity.MyBill;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewBookChosenAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.home.activity.HomeActivity;
import nlu.hmuaf.android_bookapp.user.order.activity.OrderList;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class OrderSummary extends AppCompatActivity {
    private Toolbar toolbar;
    private StepsView stepView;
    private List<String> listStepView = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView address;

    private TextView paymentMethod;
    private ImageButton changePaymentMethod;
    private TextView priceDetail;
    private TextView price;
    private Button placeOrder;
    private List<CartItems> listBook = new ArrayList<>();
    private Button buttonToMyBill, buttonToHome;
    private TextView phiVanChuyen;

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


        String[] arrayString = {"Bước 1", "Bước 2", "Bước 3", "Bước 4"};
        stepView.setLabels(arrayString) // Đặt nhãn cho StepsView
                .setBarColorIndicator(Color.parseColor("#E8E4E9")) // Đặt màu mặc định cho thanh chỉ báo (màu xám)
                .setProgressColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho chỉ báo tiến độ (màu xám)
                .setLabelColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho nhãn (màu xám)
                .setCompletedPosition(3) // Đặt vị trí đã hoàn thành
                .drawView(); // Vẽ StepsView


        recyclerView = findViewById(R.id.recycleViewBook);
        address = findViewById(R.id.textViewAddress);
        paymentMethod = findViewById(R.id.textViewPaymentMethod);
        priceDetail = findViewById(R.id.tv_priceDetails);
        price = findViewById(R.id.tv_price);
        buttonToMyBill = findViewById(R.id.buttonToMyBill);
        buttonToHome = findViewById(R.id.buttonToHome);
        phiVanChuyen = findViewById(R.id.textViewPhiVanChuyen);

        listBook = (ArrayList<CartItems>) getIntent().getSerializableExtra("listBook");
        RecycleViewBookChosenAdapter adapter2 = new RecycleViewBookChosenAdapter(this, listBook);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setAdapter(adapter2);
        phiVanChuyen.setText("Phí vận chuyển: " + MyUtils.convertToVND(30000));


        ListAddressResponseDTO address1 = (ListAddressResponseDTO) getIntent().getSerializableExtra("address");
        address.setText(address1.getAddressDetails());
        paymentMethod.setText(getIntent().getStringExtra("paymentMethod"));
        int quantityBook = 0;
        for (int i = 0; i < listBook.size(); i++) {
            listBook.get(i).getQuantity();
            quantityBook += listBook.get(i).getQuantity();
        }
        priceDetail.setText("Price Details: " + quantityBook + " sản phẩm");
        double totalPriceBook = 0;
        for (int i = 0; i < listBook.size(); i++) {
            double priceBook = 0;
            if ((Double) listBook.get(i).getDiscountedPrice() != null && listBook.get(i).getDiscountedPrice() != 0) {
                priceBook = listBook.get(i).getDiscountedPrice();
            } else {
                priceBook = listBook.get(i).getOriginalPrice();
            }
            priceBook = priceBook * listBook.get(i).getQuantity();
            totalPriceBook += priceBook;
        }
        totalPriceBook += 30000;
        price.setText("Tổng tiền: " + MyUtils.convertToVND(totalPriceBook));


//     Tạo action cho nút về trang Home
        buttonToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummary.this, HomeActivity.class);
                startActivity(intent);
            }
        });

//        Tạo button cho nút sang MyBill
        buttonToMyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummary.this, OrderList.class);
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
