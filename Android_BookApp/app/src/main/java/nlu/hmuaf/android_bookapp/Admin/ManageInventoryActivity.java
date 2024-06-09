package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nlu.hmuaf.android_bookapp.R;

public class ManageInventoryActivity extends AppCompatActivity {

    private TextView tvInStock;
    private TextView tvHiddenProducts;
    private TextView tvOutOfStock;
    private TextView tvLowStock;
    private TextView tvSKU;
    private Button btnBulkUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_manage_inventory);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lý Tồn Kho");

        tvInStock = findViewById(R.id.tvInStock);
        tvHiddenProducts = findViewById(R.id.tvHiddenProducts);
        tvOutOfStock = findViewById(R.id.tvOutOfStock);
        tvLowStock = findViewById(R.id.tvLowStock);
        tvSKU = findViewById(R.id.tvSKU);
        btnBulkUpdate = findViewById(R.id.btnBulkUpdate);
        tvInStock.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, InStockActivity.class);
            startActivity(intent);
        });

        tvHiddenProducts.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, HiddenProductsActivity.class);
            startActivity(intent);
        });

        tvOutOfStock.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, OutOfStockActivity.class);
            startActivity(intent);
        });


        tvLowStock.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, LowStockActivity.class);
            startActivity(intent);
        });

        tvSKU.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, SKUActivity.class);
            startActivity(intent);
        });

        btnBulkUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ManageInventoryActivity.this, BulkUpdateActivity.class);
            startActivity(intent);
        });
    }
}
