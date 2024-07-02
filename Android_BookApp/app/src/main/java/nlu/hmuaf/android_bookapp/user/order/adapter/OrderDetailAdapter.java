package nlu.hmuaf.android_bookapp.user.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.CartItemResponseDTO;
import nlu.hmuaf.android_bookapp.user.order.bean.OrderItem;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderItemViewHolder> {

    private final List<CartItemResponseDTO> orderItemList;

    public OrderDetailAdapter(List<CartItemResponseDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        holder.bind(orderItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productQuantity, productPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
        }

        public void bind(CartItemResponseDTO orderItem) {
            // Load hình ảnh sản phẩm
            Picasso.get().load(orderItem.getThumbnail()).into(productImage);

            productName.setText(orderItem.getTitle());
            productQuantity.setText("Số lượng: " + orderItem.getQuantity());
            double price = orderItem.getDiscountedPrice() != 0.0 ? orderItem.getDiscountedPrice() : orderItem.getOriginalPrice();
            productPrice.setText("Thành tiền:" + MyUtils.convertToVND(price * orderItem.getQuantity()));
        }

    }
}