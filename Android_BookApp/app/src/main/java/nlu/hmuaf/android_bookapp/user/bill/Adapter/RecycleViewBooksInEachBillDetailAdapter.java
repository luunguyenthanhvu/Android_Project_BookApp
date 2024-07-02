package nlu.hmuaf.android_bookapp.user.bill.Adapter;

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

public class RecycleViewBooksInEachBillDetailAdapter extends RecyclerView.Adapter<RecycleViewBooksInEachBillDetailAdapter.MyViewHolder>{
    private Activity context;

    private List<BillDetails> billDetailsList ;

    public RecycleViewBooksInEachBillDetailAdapter(Activity context, List<BillDetails> billDetailsList) {
        this.billDetailsList = billDetailsList;
        this.context = context; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_book_in_item_bill_detail, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        BillDetails billDetails = billDetailsList.get(position);
//        holder.textViewBookName.setText(billDetails.getBooks().getTitle());
//        holder.quantity.setText("Số lưu: "+String.valueOf(billDetails.getQuantity()));
//        holder.priceFinal.setText(String.valueOf("Đơn giá: "+billDetails.getBooks().getPrice())+" VNĐ");

    }

    @Override
    public int getItemCount() {
        return billDetailsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewBook;
        private TextView textViewBookName, quantity,priceFinal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBook = itemView.findViewById(R.id.imgViewBookInEachBillDetail);
            textViewBookName = itemView.findViewById(R.id.textViewBookNameInEachBillDetail);
            quantity = itemView.findViewById(R.id.textViewQuantityInEachBillDetail);
            priceFinal = itemView.findViewById(R.id.textViewPriceBaseOnQuantity);


        }

    }
}
