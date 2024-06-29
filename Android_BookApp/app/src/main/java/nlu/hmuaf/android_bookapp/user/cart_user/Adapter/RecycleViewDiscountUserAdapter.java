package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.beans.Discounts;
import nlu.hmuaf.android_bookapp.user.cart_user.activity.ICouponClickListener;
import nlu.hmuaf.android_bookapp.R;

public class RecycleViewDiscountUserAdapter extends RecyclerView.Adapter<RecycleViewDiscountUserAdapter.MyViewHolder> {
    private Activity context;

    private List<Discounts> list;

    private ICouponClickListener couponClickListener;
    private boolean changeColor = false;

    public RecycleViewDiscountUserAdapter(Activity context, List<Discounts> list) {
        this.list = list;
        this.context = context;
    }


    public void setCouponClickListener(ICouponClickListener listener) {
        this.couponClickListener = listener;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDescription, textViewExpiredDate, textViewCondition;
        private Button btn_use;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textViewDetailCoupon);
            textViewExpiredDate = itemView.findViewById(R.id.textViewExpiredDateCoupon);
            textViewCondition = itemView.findViewById(R.id.textViewCondition);
            btn_use = itemView.findViewById(R.id.buttonUse);

        }
    }
    @NonNull
    @Override
    public RecycleViewDiscountUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_discount_for_recycle_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewDiscountUserAdapter.MyViewHolder holder, int position) {
        holder.textViewDescription.setText(list.get(position).getDescription());
        holder.textViewExpiredDate.setText("HSD: " + String.valueOf(list.get(position).getExpireDate()));
        holder.textViewCondition.setText("Điều kiện: " + list.get(position).getCondition());
        holder.btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if(changeColor !=true){
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFDFDF"));
                    changeColor = true;
                } else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    changeColor = false;
                }

                if (currentPosition != RecyclerView.NO_POSITION && couponClickListener != null) {
                    couponClickListener.onCouponUsed(
                            list.get(currentPosition).getDescription(),
                            String.valueOf(list.get(currentPosition).getExpireDate()),
                            "Điều kiện: " + list.get(currentPosition).getCondition()
                    );
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    
}
