package nlu.hmuaf.android_bookapp.CartUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.CartUser.Adapter.RecycleViewBookForMyCartAdapter;
import nlu.hmuaf.android_bookapp.CartUser.Bean.Books;
import nlu.hmuaf.android_bookapp.R;

public class MyCart extends AppCompatActivity {
    private Toolbar toolbar;


    private Button btnConfirm;
    private RecyclerView listBookInACart;
    private List<Books> listBook = new ArrayList<>();
    private List<Books> listBookChoose = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);


        toolbar = findViewById(R.id.toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
        listBookInACart = findViewById(R.id.listViewBookInCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadDataBook();
        RecycleViewBookForMyCartAdapter adapter = new RecycleViewBookForMyCartAdapter(this, listBook);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listBookInACart.setLayoutManager(linearLayoutManager);
        listBookInACart.setAdapter(adapter);




        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCart.this, ReviewYourOrder.class);
                SparseIntArray selectedQuantities = adapter.getQuantityStates();
                ArrayList<Books> selectedBooks = (ArrayList<Books>) adapter.getSelectedBooks();
                HashMap<Integer, Integer> quantityMap = new HashMap<>();
                for (int i = 0; i < selectedQuantities.size(); i++) {
                    int key = selectedQuantities.keyAt(i);
                    int value = selectedQuantities.get(key);
                    quantityMap.put(key, value);
                }

                intent.putExtra("selectedQuantities", quantityMap);
                intent.putExtra("listBookChoose", (ArrayList<Books>) selectedBooks);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Kết thúc activity hiện tại và quay lại màn hình trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadDataBook() {
        listBook.add(new Books(1,1,1,"#123","Thám tử đã chết tập 7","Truyện nối tiếp cuộc hành trình trợ thủ và những người bạn anh ấy",109000,  Date.valueOf ("2024-5-19"),"Nigojuu",""));
        listBook.add(new Books(1,1,1,"#123","Thám tử đã chết tập 7","Truyện nối tiếp cuộc hành trình trợ thủ và những người bạn anh ấy",109000,  Date.valueOf ("2024-5-19"),"Nigojuu",""));
        listBook.add(new Books(1,1,1,"#123","Thám tử đã chết tập 7","Truyện nối tiếp cuộc hành trình trợ thủ và những người bạn anh ấy",109000,  Date.valueOf ("2024-5-19"),"Nigojuu",""));

    }



}
