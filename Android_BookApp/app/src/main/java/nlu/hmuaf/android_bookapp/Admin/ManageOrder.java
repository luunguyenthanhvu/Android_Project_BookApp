package nlu.hmuaf.android_bookapp.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;

public class ManageOrder extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;
    private List<Order> allOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_list);

        tabLayout = findViewById(R.id.tab_layout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Danh sách tất cả đơn hàng
        allOrders = getOrdersForTab(0);
        ordersAdapter = new OrdersAdapter(allOrders);
        recyclerView.setAdapter(ordersAdapter);

        // thanh tabLayout chứa các trạng thái của đơn hàng
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                List<Order> newOrdersList = getOrdersForTab(position);
                ordersAdapter.updateOrders(newOrdersList);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
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
    }

    private void updateTabTitles() {
        tabLayout.getTabAt(0).setText("Tất cả (" + getOrdersForTab(0).size() + ")");
        tabLayout.getTabAt(1).setText("Chờ xác nhận (" + getOrdersForTab(1).size() + ")");
        tabLayout.getTabAt(2).setText("Chờ lấy hàng (" + getOrdersForTab(2).size() + ")");
        tabLayout.getTabAt(3).setText("Đang giao (" + getOrdersForTab(3).size() + ")");
        tabLayout.getTabAt(4).setText("Đã giao (" + getOrdersForTab(4).size() + ")");
        tabLayout.getTabAt(5).setText("Đã hủy (" + getOrdersForTab(5).size() + ")");
        tabLayout.getTabAt(6).setText("Hết hàng (" + getOrdersForTab(6).size() + ")");
    }

    // Mô phỏng lấy ra đơn hàng
    private List<Order> getOrdersForTab(int tabPosition) {
        List<Order> orders = new ArrayList<>();
        if (allOrders == null) {
            // code thêm ở đây
            allOrders = new ArrayList<>();
            // test
            allOrders.add(new Order("Shop Tùng Bơ", "Standee Firefly", "firefly.jpg", 1, "₫56,000", "đ56,000", "Chờ xác nhận", "2222220"));
            allOrders.add(new Order("YX9 Shop", "Tranh/Poster Vải Anime - Game", "image_url", 2, "₫60,000", "₫120,000", "Chờ lấy hàng", "2222221"));
            allOrders.add(new Order("Amak Book", "Date a live", "image_url", 3, "₫85,800", "₫157,872", "Đang giao", "2222222"));
            allOrders.add(new Order("Shop Genshin", "Standee Nilou", "image_url", 1, "₫54,000", "₫54,000", "Đã giao", "2222223"));
        }
        switch (tabPosition) {
            // Cập nhật các trạng thái của đơn hàng ở trên
            // sau đó cho từng đơn hàng vào trạng thái tương ứng
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

    public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

        private List<Order> ordersList;

        public OrdersAdapter(List<Order> ordersList) {
            this.ordersList = ordersList;
        }

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_order, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            holder.bind(ordersList.get(position));
        }

        @Override
        public int getItemCount() {
            return ordersList.size();
        }

        public void updateOrders(List<Order> newOrdersList) {
            this.ordersList = newOrdersList;
            notifyDataSetChanged();
        }

        class OrderViewHolder extends RecyclerView.ViewHolder {
            private ImageView productImageView;
            private TextView productNameTextView, productQuantityTextView, productPriceTextView, productTotalTextView, shopNameTextView, statusTextView, codeProductTextView;
            private Button getBookButton;

            public OrderViewHolder(@NonNull View itemView) {
                super(itemView);
                productImageView = itemView.findViewById(R.id.product_image);
                productNameTextView = itemView.findViewById(R.id.product_name);
                productQuantityTextView = itemView.findViewById(R.id.product_quantity);
                productPriceTextView = itemView.findViewById(R.id.product_price);
                productTotalTextView = itemView.findViewById(R.id.product_total);
                shopNameTextView = itemView.findViewById(R.id.name_user);
                statusTextView = itemView.findViewById(R.id.status);
                codeProductTextView = itemView.findViewById(R.id.id_product_code);
                getBookButton = itemView.findViewById(R.id.get_book);
            }

            public void bind(Order order) {
                // Tên người dùng và trạng thái đơn hàng
                shopNameTextView.setText(order.getUserName());
                statusTextView.setText(order.getStatus());

                // Tên sản phẩm, số lượng, giá và tổng giá
                productNameTextView.setText(order.getProductName());
                productQuantityTextView.setText("Số lượng: " + order.getQuantity());
                productPriceTextView.setText("Giá: " + order.getPrice());
                productTotalTextView.setText("Tổng giá: " + order.getTotal());
                codeProductTextView.setText(order.getProductCode());

                // Đặt tên nút dựa trên trạng thái đơn hàng
                if (order.getStatus().equals("Chờ xác nhận")) {
                    getBookButton.setText("Lấy hàng");
                    getBookButton.setVisibility(View.VISIBLE);
                } else if (order.getStatus().equals("Chờ lấy hàng")) {
                    getBookButton.setText("Chuẩn bị giao");
                    getBookButton.setVisibility(View.VISIBLE);
                } else if (order.getStatus().equals("Đang giao")) {
                    getBookButton.setText("Đã giao");
                    getBookButton.setVisibility(View.VISIBLE);
                } else if (order.getStatus().equals("Đã giao")) {
                    getBookButton.setText("Xóa");
                    getBookButton.setVisibility(View.VISIBLE);
                } else if (order.getStatus().equals("Đã hủy")) {
                    getBookButton.setText("Xóa");
                    getBookButton.setVisibility(View.VISIBLE);
                } else {
                    getBookButton.setVisibility(View.GONE);
                }

                // nút Button Thay đổi trạng thái đơn hàng và cập nhật giao diện
                getBookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Bấm nút Lấy hàng ở đơn hàng "Chờ xác nhận"
                        if (order.getStatus().equals("Chờ xác nhận")) {
                            order.setStatus("Chờ lấy hàng");
                            // Bấm nút Chuẩn bị giao ở đơn hàng "Chờ lấy hàng"
                        } else if (order.getStatus().equals("Chờ lấy hàng")) {
                            order.setStatus("Đang giao");
                            // Bấm nút Đã giao ở đơn hàng "Đang giao"
                        } else if (order.getStatus().equals("Đang giao")) {
                            order.setStatus("Đã giao");
                            // Bấm nút Xóa ở đơn hàng "Đã giao"
//                        } else if (order.getStatus().equals("Đã giao")) {
//                            order.setStatus("Chuẩn bị giao");
//                            getBookButton.setVisibility(View.GONE);
//                        } else if (order.getStatus().equals("Chờ lấy hàng")) {
//                            order.setStatus("Chuẩn bị giao");
//                            getBookButton.setVisibility(View.GONE);
                        } else {
                            getBookButton.setVisibility(View.GONE);
                        }
                        // Cập nhật trạng thái tiêu đề của các tab
                        updateTabTitles();
                        // Cập nhật lại danh sách đơn hàng trong tab hiện tại
                        int currentTab = tabLayout.getSelectedTabPosition();
                        ordersAdapter.updateOrders(getOrdersForTab(currentTab));
                    }
                });
            }
        }
    }
}
