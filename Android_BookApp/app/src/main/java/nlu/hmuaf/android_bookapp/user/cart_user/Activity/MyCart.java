package nlu.hmuaf.android_bookapp.user.cart_user.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.Adapter.RecycleViewBookForMyCartAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.Bean.Books;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class MyCart extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnConfirm;
    private RecyclerView listBookInACart;
    private List<CartItems> listBook = new ArrayList<>();
    private List<Books> listBookChoose = new ArrayList<>();
    private CartService cartService;
    private RecycleViewBookForMyCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);
        toolbar = findViewById(R.id.toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
        listBookInACart = findViewById(R.id.listViewBookInCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartService = new CartService(getApplicationContext());
        adapter = new RecycleViewBookForMyCartAdapter(this, listBook, cartService);
        // Initialize adapter globally
        // Set up RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listBookInACart.setLayoutManager(linearLayoutManager);
        listBookInACart.setAdapter(adapter);

        // Get the user Cart in Room Database
        getCartItemData();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCart.this, ReviewYourOrder.class);
                SparseIntArray selectedQuantities = adapter.getQuantityStates();
                ArrayList<CartItems> selectedBooks = (ArrayList<CartItems>) adapter.getSelectedCartItem();
                HashMap<Integer, Integer> quantityMap = new HashMap<>();
                for (int i = 0; i < selectedQuantities.size(); i++) {
                    int key = selectedQuantities.keyAt(i);
                    int value = selectedQuantities.get(key);
                    quantityMap.put(key, value);
                }

                intent.putExtra("selectedQuantities", quantityMap);
//                intent.putExtra("listBookChoose", (ArrayList<CartItems>) selectedBooks);
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

    private void getCartItemData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            listBook = cartService.getUserCart(MyUtils.getTokenResponse(getApplicationContext()).getUserId());

            runOnUiThread(() -> {
                adapter.updateData(listBook);
            });
        });
    }

}

