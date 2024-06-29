package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;

public class RecycleViewBookChosenAdapter extends RecyclerView.Adapter<RecycleViewBookChosenAdapter.MyViewHolder> {
    private Activity context;

    private List<CartItems> listBook;
    private HashMap<Integer, Integer> quantityBookChosen;

    public RecycleViewBookChosenAdapter(Activity context, List<CartItems> list, HashMap<Integer, Integer> quantityBookChosen) {
        this.listBook = list;
        this.context = context;
        this.quantityBookChosen = quantityBookChosen;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageBook;
        private TextView textViewBookName, quantity, price, finalPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBook = itemView.findViewById(R.id.imageViewBookChosen);
            textViewBookName = itemView.findViewById(R.id.txt_TenSach_DaChon);
            quantity = itemView.findViewById(R.id.txt_soLuong);
            price = itemView.findViewById(R.id.txt_donGia_SachDaChon);
            finalPrice = itemView.findViewById(R.id.txt_giaTien_SachDaChon);
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
//        holder.imageBook.setImageResource(R.drawable.saber);
        holder.textViewBookName.setText(listBook.get(position).getTitle());
        holder.quantity.setText("Số lượng: " + String.valueOf(quantityBookChosen.get(position)));
        holder.price.setText(String.valueOf("Đơn giá: " + listBook.get(position).getOriginalPrice()) + " VNĐ");
        holder.finalPrice.setText(String.valueOf("Tổng tiền: " + quantityBookChosen.get(position) * listBook.get(position).getDiscountedPrice()) + " VNĐ");
    }


    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < listBook.size(); i++) {
            total += listBook.get(i).getDiscountedPrice() * quantityBookChosen.get(i);
        }
        return total;
    }

    public int countQuantity() {
        int total = 0;
        for (int i = 0; i < listBook.size(); i++) {
            total += quantityBookChosen.get(i);
        }
        return total;
    }


}
