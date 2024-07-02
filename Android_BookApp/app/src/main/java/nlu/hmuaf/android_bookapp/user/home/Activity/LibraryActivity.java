package nlu.hmuaf.android_bookapp.user.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.user.home.adapter.LibraryAdapter;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.activity.LogOutActivity;
import nlu.hmuaf.android_bookapp.user.profile.activity.ProfileActivity;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class LibraryActivity extends AppCompatActivity {


    private RecyclerView recyclerViewLibrary;
    private LibraryAdapter libraryAdapter;
    private int quantityBookInCart = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);

        recyclerViewLibrary = findViewById(R.id.RecyclerViewLibrary);
        recyclerViewLibrary.setLayoutManager(new LinearLayoutManager(this));

        libraryAdapter = new LibraryAdapter(new ArrayList<>());
        recyclerViewLibrary.setAdapter(libraryAdapter);
        ImageView tvPrevious = findViewById(R.id.tv_previous);

        // Đặt OnClickListener cho TextView
        tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing or perform another action
                finish();
            }
        });
        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        TokenResponseDTO tokenResponse = MyUtils.getTokenResponse(this);
        // Set click listeners for footer
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                if (tokenResponse != null) {
                    Intent intent = new Intent(LibraryActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LibraryActivity.this, LogOutActivity.class);
                    startActivity(intent);
                }
            }
        });
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity
                Intent intent = new Intent(LibraryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchActivity
                Intent intent = new Intent(LibraryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.isUserLoggedIn(LibraryActivity.this)) {
                    // Navigate to HomeActivity
                    Intent intent = new Intent(LibraryActivity.this, LibraryActivity.class);
                    startActivity(intent);
                }
            }
        });
//            Đổi màu icon navigation hiện tại
        checkCurrentActivity();
    }

    public int getQuantityBookInCart() {
        return quantityBookInCart;
    }

    public void setQuantityBookInCart(int quantityBookInCart) {
        this.quantityBookInCart = quantityBookInCart;
    }

    public void checkCurrentActivity() {
        Activity currentActivity = (Activity) this;
        if (currentActivity instanceof LibraryActivity) {
            // Đây là HomeActivity
            LinearLayout homeLayout = findViewById(R.id.libraryLayout);
            ImageView imageView = homeLayout.findViewById(R.id.wishListIcon);
            TextView textView = homeLayout.findViewById(R.id.txt_wishListIcon);

            String colorString = "#B868E9";
            int color = Color.parseColor(colorString);
            imageView.setColorFilter(color);
            textView.setTextColor(color);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(14);
        }
    }
}
