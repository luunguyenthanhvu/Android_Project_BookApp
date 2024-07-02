package nlu.hmuaf.android_bookapp.user.order.adapter;

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

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListOrderResponseDTO;
import nlu.hmuaf.android_bookapp.user.order.activity.OrderDetail;
import nlu.hmuaf.android_bookapp.user.order.activity.OrderList;
import nlu.hmuaf.android_bookapp.user.order.bean.Order;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private List<ListOrderResponseDTO> ordersList;

    public OrderListAdapter(List<ListOrderResponseDTO> ordersList, Context context) {
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

    public void updateOrders(List<ListOrderResponseDTO> newOrdersList) {
        this.ordersList = newOrdersList;
        notifyDataSetChanged();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView productNameTextView, productQuantityTextView, productPriceTextView, productTotalTextView, shopNameTextView, statusTextView, orderIdTexView;
        private Button getDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productQuantityTextView = itemView.findViewById(R.id.product_quantity);
            productPriceTextView = itemView.findViewById(R.id.product_price);
            productTotalTextView = itemView.findViewById(R.id.product_total);
//            shopNameTextView = itemView.findViewById(R.id.name_user);
            statusTextView = itemView.findViewById(R.id.status);
            orderIdTexView = itemView.findViewById(R.id.id_order);
            getDetail = itemView.findViewById(R.id.get_detail);
        }

        public void bind(ListOrderResponseDTO responseDTO) {
            // Load hình ảnh sản phẩm
            Picasso.get().load(responseDTO.getBookList().get(0).getThumbnail()).into(productImageView);

            // Tên người dùng và trạng thái đơn hàng
            statusTextView.setText(responseDTO.getStatus());

            // Tên sản phẩm, số lượng, giá và tổng giá
            productNameTextView.setText(responseDTO.getBookList().get(0).getTitle());
            productQuantityTextView.setText("Số lượng: " + responseDTO.getBookList().get(0).getQuantity());
            double price = responseDTO.getBookList().get(0).getDiscountedPrice() != 0.0 ?
                    responseDTO.getBookList().get(0).getDiscountedPrice() :
                    responseDTO.getBookList().get(0).getOriginalPrice();
            productPriceTextView.setText("Giá: " + MyUtils.convertToVND(price));
            double totalPrice = responseDTO.getBookList().get(0).getQuantity() * price;
            productTotalTextView.setText("Thành tiền: " + MyUtils.convertToVND(totalPrice));
            orderIdTexView.setText("" + ((int) responseDTO.getOrderId()));


            // nút Button xem chi tiết đơn hàng
            getDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, OrderDetail.class);
                    intent.putExtra("responseDTO", responseDTO);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void updateData(List<ListOrderResponseDTO> newData) {
        this.ordersList.clear();
        this.ordersList.addAll(newData);
        notifyDataSetChanged();
    }


}

