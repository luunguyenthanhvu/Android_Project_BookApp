package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyCart extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btn_remove;
    private Button btn_apply_coupon;
    private TextView textViewAmount;
    private TextView textViewDelivery;

    private TextView textViewPrice;
    private Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);


        toolbar = findViewById(R.id.toolbar);

        btn_remove = findViewById(R.id.button_remove);
        btn_apply_coupon = findViewById(R.id.button_coupon);
        textViewAmount = findViewById(R.id.textViewAmount);
        textViewDelivery = findViewById(R.id.textViewDelivery);
        textViewPrice = findViewById(R.id.textViewGiaTien);
        btnConfirm = findViewById(R.id.btn_confirm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Khi người dùng bấm chọn phiếu giảm giá
        btn_apply_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                Chuyển đến Intent_ListView_Coupon để lấy phiếu giảm giá
//                Intent intent = new Intent(MyCart.this, Intent_ListView_Coupon.class);
////                gửi đối tượng intent kèm requrstCode
//                 startActivityForResult(intent,99);

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCart.this, ReviewYourOrder.class);
                startActivity(intent);
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==99 && resultCode ==34) {
//           Tạo fragment cho coupon đã chọn

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Kết thúc activity hiện tại và quay lại màn hình trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
