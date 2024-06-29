package nlu.hmuaf.android_bookapp.admin.order.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.admin.order.adapter.OrderListAdapter;
import nlu.hmuaf.android_bookapp.admin.order.bean.Order;
import nlu.hmuaf.android_bookapp.R;

public class OrderList extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private OrderListAdapter ordersAdapter;
    private List<Order> allOrders;
    private ImageView arrowLeftIcon, searchIcon;
    private EditText searchEditText;
    private List<Order> currentOrderList; // danh sách đơn hàng cho chức năng tìm kiếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_list);

        // Xử lý sự kiện nhấn vào nút quay lại
        arrowLeftIcon = findViewById(R.id.arrowleft);
        arrowLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                onBackPressed();
            }
        });

        tabLayout = findViewById(R.id.tab_layout);

        // thanh recyclerView chứa các đơn hàng
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Danh sách tất cả đơn hàng
        allOrders = getOrdersForTab(0);
        ordersAdapter = new OrderListAdapter(allOrders, this);
        recyclerView.setAdapter(ordersAdapter);

        // Khởi tạo danh sách đơn hàng hiện tại
        currentOrderList = new ArrayList<>(allOrders);

        // thanh tabLayout chứa các trạng thái của đơn hàng
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                List<Order> newOrdersList = getOrdersForTab(position);
                ordersAdapter.updateOrders(newOrdersList);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        // Các tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Tất cả (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Chờ xác nhận (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Chờ lấy hàng (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang giao (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã hủy (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Hết hàng (0)"));

        // Cập nhật tiêu đề tab với số lượng đơn hàng tương ứng
        updateTabTitles();

        // Xử lý sự kiện tìm kiếm
        searchEditText = findViewById(R.id.search_edit_text);
        searchIcon = findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                if (!searchText.isEmpty()) {
                    List<Order> filteredOrders = searchOrdersById(searchText);
                    ordersAdapter.updateOrders(filteredOrders);
                }
            }
        });
    }

    public void updateTabTitles() {
        tabLayout.getTabAt(0).setText("Tất cả (" + getOrdersForTab(0).size() + ")");
        tabLayout.getTabAt(1).setText("Chờ xác nhận (" + getOrdersForTab(1).size() + ")");
        tabLayout.getTabAt(2).setText("Chờ lấy hàng (" + getOrdersForTab(2).size() + ")");
        tabLayout.getTabAt(3).setText("Đang giao (" + getOrdersForTab(3).size() + ")");
        tabLayout.getTabAt(4).setText("Đã giao (" + getOrdersForTab(4).size() + ")");
        tabLayout.getTabAt(5).setText("Đã hủy (" + getOrdersForTab(5).size() + ")");
        tabLayout.getTabAt(6).setText("Hết hàng (" + getOrdersForTab(6).size() + ")");
    }

    // Mô phỏng lấy ra đơn hàng
    public List<Order> getOrdersForTab(int tabPosition) {
        List<Order> orders = new ArrayList<>();
        if (allOrders == null) {
            // code thêm ở đây
            // test
            getListOrder();

        }
        switch (tabPosition) {
            // Cập nhật các trạng thái của đơn hàng ở trên
            // sau đó hiển thị số lượng tương cho từng đơn hàng
            case 0:
                orders.addAll(allOrders);
                break;
            case 1:
                // Chờ xác nhận
                for (Order order : allOrders) {
                    if (order.getStatus().equals("Chờ xác nhận")) {
                        orders.add(order);
                    }
                }
                break;
            case 2:
                // Chờ lấy hàng
                for (Order order : allOrders) {
                    if (order.getStatus().equals("Chờ lấy hàng")) {
                        orders.add(order);
                    }
                }
                break;
            case 3:
                // Đang giao
                for (Order order : allOrders) {
                    if (order.getStatus().equals("Đang giao")) {
                        orders.add(order);
                    }
                }
                break;
            case 4:
                // Đã giao
                for (Order order : allOrders) {
                    if (order.getStatus().equals("Đã giao")) {
                        orders.add(order);
                    }
                }
                break;
            case 5:
                // Đã hủy
                for (Order order : allOrders) {
                    if (order.getStatus().equals("Đã hủy")) {
                        orders.add(order);
                    }
                }
                break;
            case 6:
                // Hết hàng
                for (Order order : allOrders) {
                    if (order.getStatus().equals("Hết hàng")) {
                        orders.add(order);
                    }
                }
                break;
        }
        return orders;
    }

    private List<Order> getListOrder() {

        allOrders = new ArrayList<>();
        allOrders.add(new Order("Shop Tùng Bơ", "Standee Firefly", R.drawable.book1, 1, "₫56,000", "đ56,000", "Chờ xác nhận", "2222220"));
        allOrders.add(new Order("YX9 Shop", "Tranh/Poster Vải Anime - Game", R.drawable.book2, 2, "₫60,000", "₫120,000", "Chờ lấy hàng", "2222221"));
        allOrders.add(new Order("Amak Book", "Date a live", R.drawable.book3, 3, "₫85,800", "₫157,872", "Đang giao", "2222222"));
        allOrders.add(new Order("Shop Genshin", "Standee Nilou", R.drawable.book4, 1, "₫54,000", "₫54,000", "Đã giao", "2222223"));

        return allOrders;
    }


    // Cập nhật lại danh sách đơn hàng trong tab hiện tại
    public int getSelectedTabPosition() {
        return tabLayout.getSelectedTabPosition();
    }


    // Tìm kiếm đơn hàng theo mã đơn hàng
    public List<Order> searchOrdersById(String searchText) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getOrderId().contains(searchText)) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }
}