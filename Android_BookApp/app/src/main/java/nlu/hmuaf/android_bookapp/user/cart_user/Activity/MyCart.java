package nlu.hmuaf.android_bookapp.user.cart_user.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.Adapter.RecycleViewBookForMyCartAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.Bean.Books;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.home.Activity.BookActivity;
import nlu.hmuaf.android_bookapp.user.home.Activity.SearchActivity;
import nlu.hmuaf.android_bookapp.user.home.Adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class MyCart extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnConfirm;
    private RecyclerView listBookInACart;
    private List<CartItems> listBook = new ArrayList<>();
    private List<Books> listBookChoose = new ArrayList<>();
    private CartService cartService;
    private RecycleViewBookForMyCartAdapter adapter;
    private TokenResponseDTO tokenResponseDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);

        tokenResponseDTO = MyUtils.getTokenResponse(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
        listBookInACart = findViewById(R.id.listViewBookInCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // to sync the cart at front end to sever (bookId, quantity)
        cartService = new CartService(getApplicationContext());
        cartService.syncLocalCartToServer(tokenResponseDTO);

        adapter = new RecycleViewBookForMyCartAdapter(this, listBook, cartService, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CartItems selectedBook = listBook.get(position);
                Intent intent = new Intent(MyCart.this, BookActivity.class);
                intent.putExtra("BOOK_ID", selectedBook.getBookId());
                startActivity(intent);
            }
        });
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
                List<CartItems> selectedBooks = adapter.getSelectedCartItems();
                intent.putExtra("listBookChoose", (ArrayList<CartItems>) selectedBooks);
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

