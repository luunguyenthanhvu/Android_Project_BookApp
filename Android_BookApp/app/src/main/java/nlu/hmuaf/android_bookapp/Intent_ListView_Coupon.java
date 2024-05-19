package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;




public class Intent_ListView_Coupon extends AppCompatActivity {
     private List<Discounts> couponList = new ArrayList<>();
     private ListViewCouponAdapter listViewCouponAdapter;
     private ListView listViewCoupon;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        getDataCoupon();
        setContentView(R.layout.list_view_coupon);
        listViewCoupon =findViewById(R.id.listCoupon);
        listViewCouponAdapter = new ListViewCouponAdapter(Intent_ListView_Coupon.this,R.layout.custom_item_coupon_for_list_view,couponList);
        listViewCoupon.setAdapter(listViewCouponAdapter);


        listViewCoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewCouponAdapter.setCouponClickListener(new ICouponClickListener() {
                    @Override
                    public void onCouponUsed(String name, String detail, String expiredDate) {

                     intent.putExtra("detailCoupon",couponList.get(position).getDescription());
                     intent.putExtra("expiredDateCoupon",String.valueOf(couponList.get(position).getExpireDate()));
                     intent.putExtra("conditionCoupon",String.valueOf(couponList.get(position).getCondition()));
//                           Gửi resultCde 34 về My cart để My cart lấy dữ liệu
                       setResult(34,intent);
                       finish();
                    }
                });


            }
        });


    }



    //     Hàm này sẽ nạp dữ liệu Coupon vào List<Discounts>
     public List<Discounts> getDataCoupon() {



         return couponList;
     }

}
