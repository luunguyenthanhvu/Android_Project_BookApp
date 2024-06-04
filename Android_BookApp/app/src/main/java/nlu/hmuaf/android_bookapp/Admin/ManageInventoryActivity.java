package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nlu.hmuaf.android_bookapp.R;

public class ManageInventoryActivity extends AppCompatActivity {

    private TextView tvInStock, tvHiddenLocked, tvOutOfStock, tvLowStock;
    private Button btnInStock, btnHiddenLocked, btnOutOfStock, btnLowStock;
    private Button btnBulkUpdate, btnDownload, btnViewMode;
    private Switch switchNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_inventory);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lý Tồn Kho");
//
//        tvInStock = findViewById(R.id.tvInStock);
//        tvHiddenLocked = findViewById(R.id.tvHiddenLocked);
//        tvOutOfStock = findViewById(R.id.tvOutOfStock);
//        tvLowStock = findViewById(R.id.tvLowStock);
//        btnInStock = findViewById(R.id.btnInStock);
//        btnHiddenLocked = findViewById(R.id.btnHiddenLocked);
//        btnOutOfStock = findViewById(R.id.btnOutOfStock);
//        btnLowStock = findViewById(R.id.btnLowStock);
//        btnBulkUpdate = findViewById(R.id.btnBulkUpdate);
//        btnDownload = findViewById(R.id.btnDownload);
////        btnViewMode = findViewById(R.id.btnViewMode);
//        switchNotification = findViewById(R.id.switchNotification);

//        btnInStock.setOnClickListener(v -> openActivity(InStockActivity.class));
//        btnHiddenLocked.setOnClickListener(v -> openActivity(HiddenLockedActivity.class));
//        btnOutOfStock.setOnClickListener(v -> openActivity(OutOfStockActivity.class));
//        btnLowStock.setOnClickListener(v -> openActivity(LowStockActivity.class));
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(ManageInventoryActivity.this, activityClass);
        startActivity(intent);
    }
}
