package nlu.hmuaf.android_bookapp.CartUser.Adapter;

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

import nlu.hmuaf.android_bookapp.CartUser.Bean.Books;
import nlu.hmuaf.android_bookapp.R;

public class RecycleViewBookChosenAdapter extends RecyclerView.Adapter<RecycleViewBookChosenAdapter.MyViewHolder>{
    private Activity context;

    private List<Books> listBook;
    private HashMap<Integer, Integer> quantityBookChosen ;

    public RecycleViewBookChosenAdapter(Activity context, List<Books> list,HashMap<Integer, Integer> quantityBookChosen) {
        this.listBook = list;
        this.context = context;
        this.quantityBookChosen=quantityBookChosen;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageBook;
        private TextView textViewBookName, quantity,price,finalPrice;

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
    public void onBindViewHolder(@NonNull RecycleViewBookChosenAdapter.MyViewHolder holder, int position){
//        holder.imageBook.setImageResource(R.drawable.saber);
        holder.textViewBookName.setText(listBook.get(position).getTitle());
        holder.quantity.setText("Số lượng: "+String.valueOf(quantityBookChosen.get(position)));
        holder.price.setText(String.valueOf("Đơn giá: "+listBook.get(position).getPrice())+" VNĐ");
        holder.finalPrice.setText(String.valueOf("Tổng tiền: "+quantityBookChosen.get(position) * listBook.get(position).getPrice())+" VNĐ");
    }



    @Override
    public int getItemCount() {
        return listBook.size();
    }
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < listBook.size(); i++) {
            total += listBook.get(i).getPrice() * quantityBookChosen.get(i);
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
