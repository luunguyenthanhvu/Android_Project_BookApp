package nlu.hmuaf.android_bookapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


import nlu.hmuaf.android_bookapp.Discounts;
import nlu.hmuaf.android_bookapp.ICouponClickListener;
import nlu.hmuaf.android_bookapp.MyCart;
import nlu.hmuaf.android_bookapp.R;

public class ListViewCouponAdapter extends ArrayAdapter<Discounts> {
    private Activity context;
    private int resource;
    private List<Discounts> list;

    private ICouponClickListener couponClickListener;
    public ListViewCouponAdapter(@NonNull Activity context, int resource, @NonNull List<Discounts> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource = resource;
        this.list =objects;


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(resource, null);
        Discounts discounts = list.get(position);
        TextView detailCoupon = view.findViewById(R.id.textViewDetailCoupon);
        TextView expiredDate = view.findViewById(R.id.textViewExpiredDateCoupon);
        TextView condition = view.findViewById(R.id.textViewCondition);
        Button btn_use = view.findViewById(R.id.buttonUse);


        detailCoupon.setText(discounts.getDescription());
        expiredDate.setText("HSD: "+String.valueOf(discounts.getExpireDate()));
        condition.setText("Điều kiện: "+discounts.getCondition());
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (couponClickListener != null) {
                    couponClickListener.onCouponUsed(list.get(position).getDescription(),String.valueOf( list.get(position).getExpireDate()), "Điều kiện: "+list.get(position).getCondition());
                }
            }
        });

        return view;


    }

    public void setCouponClickListener(ICouponClickListener listener) {
        this.couponClickListener = listener;
    }
}
