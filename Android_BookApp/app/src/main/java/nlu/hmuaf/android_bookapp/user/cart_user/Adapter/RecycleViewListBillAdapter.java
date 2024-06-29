
package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Bills;

public class RecycleViewListBillAdapter  extends RecyclerView.Adapter<RecycleViewListBillAdapter.MyViewHolder> {
    private Activity context;

    private List<Bills> billsList;

    public RecycleViewListBillAdapter(Activity context, List<Bills> billsList) {
        this.context = context;
        this.billsList = billsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_recycle_view, parent, false);
        return new RecycleViewListBillAdapter. MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.recyclerViewBookBought.setAdapter(new RecycleViewBooksInEachBillAdapter(context, billsList.get(position).getBillDetailsList()));
        holder.textViewTotalPrice.setText("Thành tiền:  "+billsList.get(position).getTotalPrice()+"đ");
        holder.textViewStatusPrice.setText("Trảng thái thanh toán "+billsList.get(position).getStatus().toString());
        holder.buttonSeeBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerViewBookBought;
        private TextView textViewTotalPrice, textViewStatusPrice;
        private ImageButton buttonSeeBillDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonSeeBillDetail = itemView.findViewById(R.id.imageButtonSeeDetail);
            recyclerViewBookBought = itemView.findViewById(R.id.recycleViewBookBought);
            textViewTotalPrice = itemView.findViewById(R.id.txt_totalPrice);
            textViewStatusPrice = itemView.findViewById(R.id.txt_statusBill);

        }

    }



}
