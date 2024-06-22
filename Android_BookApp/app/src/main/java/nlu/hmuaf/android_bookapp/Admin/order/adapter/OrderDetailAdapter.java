package nlu.hmuaf.android_bookapp.Admin.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import nlu.hmuaf.android_bookapp.Admin.order.bean.OrderItem;
import nlu.hmuaf.android_bookapp.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderItemViewHolder> {

    private List<OrderItem> orderItemList;

    public OrderDetailAdapter(List<OrderItem> orderItemList) {
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
        OrderItem orderItem = orderItemList.get(position);
        holder.productName.setText(orderItem.getProductName());
        holder.productPrice.setText(orderItem.getProductPrice());
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}