package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class RecycleViewBookChosenAdapter extends RecyclerView.Adapter<RecycleViewBookChosenAdapter.MyViewHolder> {
    private Activity context;

    private List<CartItems> listBook;

    public RecycleViewBookChosenAdapter(Activity context, List<CartItems> list) {
        this.listBook = list != null ? list : new ArrayList<>();
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageBook;
        private TextView textViewBookName, quantity, price, finalPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBook = itemView.findViewById(R.id.imageViewBookChosen);
            textViewBookName = itemView.findViewById(R.id.txt_TenSach_DaChon);
            quantity = itemView.findViewById(R.id.txt_soLuong);
            price = itemView.findViewById(R.id.txt_tongTien);
        }
    }

    @NonNull
    @Override
    public RecycleViewBookChosenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_chosen, parent, false);
        return new RecycleViewBookChosenAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewBookChosenAdapter.MyViewHolder holder, int position) {
        CartItems item = listBook.get(position);
        Picasso.get().load(item.getThumbnail()).into(holder.imageBook);
        holder.textViewBookName.setText(item.getTitle());
        holder.quantity.setText("Số lượng: " + String.valueOf(item.getQuantity()));
        double priceToShow;
        if ((Double) item.getDiscountedPrice() != null && item.getDiscountedPrice() != 0) {
            priceToShow = item.getDiscountedPrice();
        } else {
            priceToShow = item.getOriginalPrice();
        }
        holder.price.setText("Tổng tiền: " + MyUtils.convertToVND(priceToShow * item.getQuantity()));


    }


    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public void updateData(List<CartItems> newList) {
        this.listBook.clear();
        this.listBook.addAll(newList);
        notifyDataSetChanged();
    }

}
