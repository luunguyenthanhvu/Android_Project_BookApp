//package Adapter;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import Bean.Coupon;
//import nlu.hmuaf.android_bookapp.ICouponClickListener;
//import nlu.hmuaf.android_bookapp.MyCart;
//import nlu.hmuaf.android_bookapp.R;
//
//public class ListViewCouponAdapter extends ArrayAdapter<Coupon> {
//    private Activity context;
//    private int resource;
//    private List<Coupon> list;
//
//    private ICouponClickListener couponClickListener;
//    public ListViewCouponAdapter(@NonNull Activity context, int resource, @NonNull List<Coupon> objects) {
//        super(context, resource, objects);
//        this.context=context;
//        this.resource = resource;
//        this.list =objects;
//
//
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//        View view = inflater.inflate(resource, null);
//        Coupon coupon = list.get(position);
//        TextView nameCoupon = view.findViewById(R.id.textViewNameCoupon);
//        TextView detailCoupon = view.findViewById(R.id.textViewDetailCoupon);
//        TextView expiredDate = view.findViewById(R.id.textViewExpiredDateCoupon);
//        Button btn_use = view.findViewById(R.id.buttonUse);
//
//        nameCoupon.setText(coupon.getName());
//        detailCoupon.setText(coupon.getDescription());
//        expiredDate.setText("HSD: "+String.valueOf(coupon.getExpireDate()));
//
//        btn_use.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (couponClickListener != null) {
//                    couponClickListener.onCouponUsed(list.get(position).getName(), list.get(position).getDescription(), String.valueOf(list.get(position).getExpireDate()));
//                }
//            }
//        });
//
//        return view;
//
//
//    }
//
//    public void setCouponClickListener(ICouponClickListener listener) {
//        this.couponClickListener = listener;
//    }
//}
