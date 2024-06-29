package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import nlu.hmuaf.android_bookapp.user.bill.activity.MyBill;
import nlu.hmuaf.android_bookapp.user.bill.enums.EBillStatus;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Bills;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Books;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Users;

public class OrderSuccessfully extends AppCompatActivity {
  private TextView price;
  private Button buttonToMyBill, buttonToHome;
  RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        price = findViewById(R.id.txt_price);
        buttonToMyBill = findViewById(R.id.buttonToMyBill);
        buttonToHome = findViewById(R.id.buttonToHome);
        price.setText("Đã thanh toán "+getIntent().getStringExtra("price")+"đ");

        List<Books> booksList = (ArrayList<Books>) getIntent().getSerializableExtra("listBook");
        HashMap<Integer,Integer> quantityEachBook = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("quantityEachBook");
        double totalPrice = getIntent().getDoubleExtra("totalPrice",0);
        Users user = new Users();
        user.setUsername("Yato");
        user.setUserId(1);
        Bills bill = new Bills();


        LocalDate dateOrder = LocalDate.now();
        LocalDate dateReceipt = LocalDate.now().plusDays(2);
        bill.setBillId(102);
        bill.setTotalPrice(totalPrice);
        bill.setStatus(EBillStatus.DELIVERED);
        bill.setDeliveryDate(dateOrder);
        bill.setReceiptDate(dateReceipt);
        bill.setUser(user);

//        Xử lý từng bill detail cho các cuốn sách đã mua
        for(int i=0;i<booksList.size();i++){

        }
        
        buttonToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonToMyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSuccessfully.this, MyBill.class);
                intent.putExtra("listBooksBought",(ArrayList<Books>)booksList);
                intent.putExtra("quantityEachBook",quantityEachBook);
                intent.putExtra("bill",bill);
                startActivity(intent);
            }
        });

    }

}
