package nlu.hmuaf.android_bookapp.HomeScreen.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.Set;

import nlu.hmuaf.android_bookapp.HomeScreen.RecyclerItemClickListener;
import nlu.hmuaf.android_bookapp.R;

public class FantasyActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private ImageView imageView;
    private RecyclerView fantasyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fantasy_activity);

        // Initialize the views
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        fantasyRecyclerView = findViewById(R.id.FantasyRecyclerView);

        // Set up any additional functionality here
        textView.setText("Search");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(editText.getText().toString());
            }
        });
        // Tìm và thêm sự kiện onClick cho chuyển đến HomeActivity
        LinearLayout homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và thêm sự kiện onClick cho chuyển đến LibraryActivity
        LinearLayout libraryLayout = findViewById(R.id.libraryLayout);
        libraryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FantasyActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

//        // Tìm và thêm sự kiện onClick cho chuyển đến ProfileActivity
//        LinearLayout profileLayout = findViewById(R.id.profileLayout);
//        profileLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

        // Assuming that the bell imageView has the id iconImageView in the RecyclerView items
        // Attach a click listener to the bell icon
        fantasyRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, fantasyRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView bellImageView = view.findViewById(R.id.iconImageView);
                bellImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String itemData = "Item " + position; // Example item data
                        saveClickedItem(itemData);
                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Do nothing
            }
        }));
    }

    private void saveClickedItem(String item) {
        SharedPreferences sharedPreferences = getSharedPreferences("ClickedItemsPrefs", MODE_PRIVATE);
        Set<String> clickedItems = sharedPreferences.getStringSet("clickedItems", new HashSet<>());
        clickedItems.add(item);
        sharedPreferences.edit().putStringSet("clickedItems", clickedItems).apply();
    }

    private void performSearch(String query) {
        // Save the item (for example, in SharedPreferences or a database)
        // For simplicity, we are using an Intent to send the data to the next Activity
        Intent intent = new Intent(FantasyActivity.this, LibraryActivity.class);
        intent.putExtra("search_query", query);
        startActivity(intent);
    }
}
