package nlu.hmuaf.android_bookapp.admin.order.activity;

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

import nlu.hmuaf.android_bookapp.admin.order.adapter.OrderDetailAdapter;
import nlu.hmuaf.android_bookapp.admin.order.bean.OrderItem;
import nlu.hmuaf.android_bookapp.R;

public class OrderDetail extends AppCompatActivity {

    private TextView nameUser, phoneNumUser, addressUser, totalPrice, priceShip, discountPriceShip,
            discount, intoPrice, paymentMethod, orderId, timeOrder, timePayment, timeDelivery, timeDelivered, copyIdOrder , statusView;
    private RecyclerView recyclerView;
    private LinearLayout detailsLayout;
    private ImageView productImage, arrowLeftIcon, dropdownIcon;
    private String statusItem[] = {"Chờ xác nhận", "Chờ lấy hàng", "Đang giao", "Đã giao"};
    private String currentStatus;

    private AutoCompleteTextView statusTextView;
    private ArrayAdapter<String> adapterItem;
    private Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_detail);

        // Khai báo
//        nameUser = findViewById(R.id.name_user);
//        phoneNumUser = findViewById(R.id.phoneNum_user);
//        addressUser = findViewById(R.id.address_user);
        totalPrice = findViewById(R.id.total_price);
        priceShip = findViewById(R.id.price_ship);
        discountPriceShip = findViewById(R.id.discount_price_ship);
        discount = findViewById(R.id.discount);
        intoPrice = findViewById(R.id.into_price);
        paymentMethod = findViewById(R.id.payment_method);
        orderId = findViewById(R.id.order_id);
//        timeOrder = findViewById(R.id.time_order);
//        timePayment = findViewById(R.id.time_payment);
//        timeDelivery = findViewById(R.id.time_delivery);
//        timeDelivered = findViewById(R.id.time_delivered);
        recyclerView = findViewById(R.id.recycler_view);
        detailsLayout = findViewById(R.id.details_layout);

        // Xử lý sự kiện nhấn vào nút quay lại
        arrowLeftIcon = findViewById(R.id.arrowleft);
        arrowLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                onBackPressed();
            }
        });

        // Nhận trạng thái từ Intent
        Intent intent = getIntent();
        currentStatus = intent.getStringExtra("order_status");

        // Khởi tạo AutoCompleteTextView và adapter
//        statusTextView = findViewById(R.id.status_view);
        adapterItem = new ArrayAdapter<String>(this, R.layout.admin_list_home, statusItem);

        // Thiết lập trạng thái hiện tại
        statusTextView.setText(currentStatus, false);
        statusTextView.setAdapter(adapterItem);

        // Xử lý sự kiện khi người dùng chọn trạng thái mới
        statusTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l) {
                String statusItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(OrderDetail.this, "" + statusItem, Toast.LENGTH_SHORT).show();
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

        // Xử lý sự kiện sao chép mã đơn hàng khi bấm vào textView copyIdOrder
        copyIdOrder = findViewById(R.id.copy_idOrder);
        copyIdOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy mã đơn hàng từ orderId
                String orderIdText = orderId.getText().toString();

                // Sao chép mã đơn hàng vào clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", orderIdText);
                clipboard.setPrimaryClip(clip);

                // Hiển thị thông báo khi sao chép thành công
                Toast.makeText(OrderDetail.this, "Đã sao chép mã đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút Button cập nhật thông tin đơn hàng
//        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý back-end
                // code thêm ở đây
                Toast.makeText(OrderDetail.this, "Đã cập nhật trạng thái đơn hàng thành công", Toast.LENGTH_SHORT).show();
                navigateToOrderList();
            }
        });

        // Nút Button xóa đơn hàng
//        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý back-end
                // code thêm ở đây
                Toast.makeText(OrderDetail.this, "Đã xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                navigateToOrderList();
            }
        });

        // Test dữ liệu
        nameUser.setText("Nguyễn Văn A");
        phoneNumUser.setText("(+84) 123456789");
        addressUser.setText("Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM");
        totalPrice.setText("500,000 VND");
        priceShip.setText("30,000 VND");
        discountPriceShip.setText("-10,000 VND");
        discount.setText("-20,000 VND");
        intoPrice.setText("500,000 VND");
        paymentMethod.setText("Thanh toán khi nhận hàng");
        orderId.setText("240513522353");
        timeOrder.setText("20-05-2024");
        timePayment.setText("20-05-2024");
        timeDelivery.setText("21-05-2024");
        timeDelivered.setText("22-05-2024");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(R.drawable.book1 , "Sản phẩm 1", 1, "30,000 VND"));
        orderItemList.add(new OrderItem(R.drawable.book2 ,"Sản phẩm 2", 2, "20,000 VND"));

        OrderDetailAdapter adapter = new OrderDetailAdapter(orderItemList);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToOrderList() {
        Intent intent = new Intent(OrderDetail.this, OrderList.class);
        startActivity(intent);
        finish();
    }

}
