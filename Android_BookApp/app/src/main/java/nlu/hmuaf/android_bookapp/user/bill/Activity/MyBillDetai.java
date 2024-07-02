package nlu.hmuaf.android_bookapp.user.bill.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.bill.Adapter.RecycleViewBooksInEachBillDetailAdapter;
import nlu.hmuaf.android_bookapp.user.bill.beans.BillDetails;

public class MyBillDetai extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView addressUser;
    private RecyclerView recycleViewListBookBought;
    private TextView statusBill;
    private TextView ngayDat;
    private TextView ngayGiao;
    private RecycleViewBooksInEachBillDetailAdapter adapter;
    private List<BillDetails> billDetailsList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_bill_detail);
        toolbar = findViewById(R.id.toolbarBillDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết hóa đơn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addressUser = findViewById(R.id.txt_infoUser);
        recycleViewListBookBought = findViewById(R.id.recycleViewListBook);
        statusBill = findViewById(R.id.txt_stateBill);
        ngayDat = findViewById(R.id.txt_ngayDat);
        ngayGiao = findViewById(R.id.txt_ngayGiao);

        loadData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleViewListBookBought.setLayoutManager(linearLayoutManager);
//        adapter này sẽ tạo danh sách Sách recycleView cho chi tiết hóa đơn
        adapter = new RecycleViewBooksInEachBillDetailAdapter(this, billDetailsList);
        recycleViewListBookBought.setAdapter(adapter);



    }
//    Nạp dữ liệu vào billDetailsList
    private void loadData(){

    }
}
