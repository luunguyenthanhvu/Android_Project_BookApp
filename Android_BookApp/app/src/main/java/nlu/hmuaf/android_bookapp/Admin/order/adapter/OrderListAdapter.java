package nlu.hmuaf.android_bookapp.admin.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nlu.hmuaf.android_bookapp.admin.order.bean.Order;
import nlu.hmuaf.android_bookapp.admin.order.activity.OrderDetail;
import nlu.hmuaf.android_bookapp.admin.order.activity.OrderList;
import nlu.hmuaf.android_bookapp.R;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private List<Order> ordersList;
    private OrderList orderList;

    public OrderListAdapter(List<Order> ordersList, OrderList orderList) {
        this.ordersList = ordersList;
        this.orderList = orderList;
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
        private TextView productNameTextView, productQuantityTextView, productPriceTextView, productTotalTextView, shopNameTextView, statusTextView, orderIdTexView;
        private Button getDetail, getBookButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productQuantityTextView = itemView.findViewById(R.id.product_quantity);
            productPriceTextView = itemView.findViewById(R.id.product_price);
            productTotalTextView = itemView.findViewById(R.id.product_total);
            shopNameTextView = itemView.findViewById(R.id.name_user);
            statusTextView = itemView.findViewById(R.id.status);
            orderIdTexView = itemView.findViewById(R.id.id_order);
            getDetail = itemView.findViewById(R.id.get_detail);
            getBookButton = itemView.findViewById(R.id.get_book);
        }

        public void bind(Order order) {
            // Load hình ảnh sản phẩm
            Picasso.get().load(order.getResourceid()).into(productImageView);

            // Tên người dùng và trạng thái đơn hàng
            shopNameTextView.setText(order.getUserName());
            statusTextView.setText(order.getStatus());

            // Tên sản phẩm, số lượng, giá và tổng giá
            productNameTextView.setText(order.getProductName());
            productQuantityTextView.setText("Số lượng: " + order.getQuantity());
            productPriceTextView.setText("Giá: " + order.getPrice());
            productTotalTextView.setText("Thành tiền: " + order.getTotal());
            orderIdTexView.setText(order.getOrderId());

            // Cập nhật tên nút Button dựa trên trạng thái đơn hàng
            if (order.getStatus().equals("Chờ xác nhận")) {
                getBookButton.setText("Lấy hàng");
                getBookButton.setVisibility(View.VISIBLE);
            } else if (order.getStatus().equals("Chờ lấy hàng")) {
                getBookButton.setText("Chuẩn bị giao");
                getBookButton.setVisibility(View.VISIBLE);
            } else if (order.getStatus().equals("Đang giao")) {
                getBookButton.setText("Đã giao");
                getBookButton.setVisibility(View.VISIBLE);
            } else if (order.getStatus().equals("Đã giao") || order.getStatus().equals("Đã hủy")) {
                getBookButton.setText("Xóa");
                getBookButton.setVisibility(View.VISIBLE);
            } else {
                getBookButton.setVisibility(View.GONE);
            }

            // nút Button xem chi tiết đơn hàng
            getDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, OrderDetail.class);
                    intent.putExtra("order_status", order.getStatus());
                    context.startActivity(intent);
                }
            });

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
                    } else {
                        getBookButton.setVisibility(View.GONE);
                    }
                    // Cập nhật trạng thái tiêu đề của các tab
                    orderList.updateTabTitles();
                    // Cập nhật lại danh sách đơn hàng trong tab hiện tại
                    int currentTab = orderList.getSelectedTabPosition();
                    updateOrders(orderList.getOrdersForTab(currentTab));
                }
            });
        }
    }


}

