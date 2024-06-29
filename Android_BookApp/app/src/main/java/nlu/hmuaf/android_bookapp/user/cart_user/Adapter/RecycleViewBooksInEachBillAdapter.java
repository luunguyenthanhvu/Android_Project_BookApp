
package nlu.hmuaf.android_bookapp.user.cart_user.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import nlu.hmuaf.android_bookapp.user.bill.beans.BillDetails;



import nlu.hmuaf.android_bookapp.R;

public class RecycleViewBooksInEachBillAdapter extends RecyclerView.Adapter<RecycleViewBooksInEachBillAdapter.MyViewHolder>{
    private Activity context;

    private List<BillDetails> billDetailsList ;
    public RecycleViewBooksInEachBillAdapter(Activity context, List<BillDetails> billDetailsList) {
        this.billDetailsList = billDetailsList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_book_in_item_bill, parent, false);

        return new RecycleViewBooksInEachBillAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BillDetails billDetails = billDetailsList.get(position);
//        Books books = billDetails.getBooks();
//        holder.textViewBookName.setText(books.getTitle());
//        holder.quantity.setText("Số lưu: "+String.valueOf(billDetails.getQuantity()));
//        holder.price.setText(String.valueOf("Đơn giá: "+books.getPrice())+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return billDetailsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewBook;
        private TextView textViewBookName, quantity,price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBook = itemView.findViewById(R.id.imgViewBookInEachBillDetail);
            textViewBookName = itemView.findViewById(R.id.textViewBookNameInEachBillDetail);
            quantity = itemView.findViewById(R.id.textViewQuantityInEachBillDetail);
            price = itemView.findViewById(R.id.textViewPriceBaseOnQuantity);


        }

    }
}
