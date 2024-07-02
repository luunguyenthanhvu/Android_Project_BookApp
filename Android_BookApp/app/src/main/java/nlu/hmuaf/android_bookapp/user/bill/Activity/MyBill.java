
package nlu.hmuaf.android_bookapp.user.bill.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.bill.Adapter.RecycleViewListBillAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Bills;

public class MyBill extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Bills> billsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_bill_user);
        toolbar = findViewById(R.id.toolbarMyBill);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hóa đơn của tôi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.listBillUser);



        loadData();
        RecycleViewListBillAdapter adapter = new RecycleViewListBillAdapter(this, billsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    public void loadData() {


    }


}
