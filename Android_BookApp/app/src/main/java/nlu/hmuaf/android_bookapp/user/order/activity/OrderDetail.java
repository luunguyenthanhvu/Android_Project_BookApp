package nlu.hmuaf.android_bookapp.user.order.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListOrderResponseDTO;
import nlu.hmuaf.android_bookapp.user.order.adapter.OrderDetailAdapter;
import nlu.hmuaf.android_bookapp.user.order.bean.OrderItem;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class OrderDetail extends AppCompatActivity {

    private TextView totalPrice, priceShip, discountPriceShip,
            discount, intoPrice, paymentMethod, orderId, timeOrder, timePayment, timeDelivery, timeDelivered, copyIdOrder, statusView;
    private TextView orderStatus;
    private RecyclerView recyclerView;
    private LinearLayout detailsLayout;
    private ImageView productImage, arrowLeftIcon, dropdownIcon;
    private String currentStatus;
    private ArrayAdapter<String> adapterItem;
    private Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_detail);
        ListOrderResponseDTO responseDTO = (ListOrderResponseDTO) getIntent().getSerializableExtra("responseDTO");

        // Khai báo
        totalPrice = findViewById(R.id.total_price);
        priceShip = findViewById(R.id.price_ship);
        discountPriceShip = findViewById(R.id.discount_price_ship);
        discount = findViewById(R.id.discount);
        intoPrice = findViewById(R.id.into_price);
        paymentMethod = findViewById(R.id.payment_method);
        orderId = findViewById(R.id.order_id);
        recyclerView = findViewById(R.id.recycler_view);
        detailsLayout = findViewById(R.id.details_layout);
        orderStatus = findViewById(R.id.order_Status);

        // Xử lý sự kiện nhấn vào nút quay lại
        arrowLeftIcon = findViewById(R.id.arrowleft);
        arrowLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                onBackPressed();
            }
        });

        // Xử lý sự kiện nhấn vào icon để hiện chi tiết
        dropdownIcon = findViewById(R.id.dropdown_icon);
        dropdownIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsLayout.getVisibility() == View.GONE) {
                    detailsLayout.setVisibility(View.VISIBLE);
                    dropdownIcon.setVisibility(View.GONE);
                }
            }
        });

        // Test dữ liệu
        totalPrice.setText(MyUtils.convertToVND(responseDTO.getTotalPrice() - 30000));
        priceShip.setText(MyUtils.convertToVND(30000));
        discount.setText(MyUtils.convertToVND(0));
        intoPrice.setText(MyUtils.convertToVND(responseDTO.getTotalPrice()));
        paymentMethod.setText(responseDTO.getPaymentMethod());
        discountPriceShip.setText(MyUtils.convertToVND(0));
        orderId.setText("" + responseDTO.getOrderId());
        orderStatus.setText(responseDTO.getStatus());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderDetailAdapter adapter = new OrderDetailAdapter(responseDTO.getBookList());
        recyclerView.setAdapter(adapter);
    }
}
