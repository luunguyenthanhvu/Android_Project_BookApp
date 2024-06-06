package nlu.hmuaf.android_bookapp.Admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nlu.hmuaf.android_bookapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvProductCode, tvClassificationInfo, tvSales7Days, tvSales30Days, tvLocation, tvAvailableStock, tvPromoStock, tvMinimumStock, tvSupplementLevel;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi Tiết Sản Phẩm");

        // Khởi tạo các thành phần giao diện
        initializeViews();

        // Nhận mã sản phẩm từ Intent
        String productCode = getIntent().getStringExtra("productCode");
        if (productCode != null) {
            displayProductCode(productCode);
        }

        btnUpdate.setOnClickListener(v -> {
            // Logic cho nút sửa, có thể mở một Activity khác để chỉnh sửa thông tin
        });
    }

    private void initializeViews() {
        tvProductCode = findViewById(R.id.tvProductCode);
        tvClassificationInfo = findViewById(R.id.tvClassificationInfo);
        tvSales7Days = findViewById(R.id.tvSales7Days);
        tvSales30Days = findViewById(R.id.tvSales30Days);
        tvLocation = findViewById(R.id.tvLocation);
        tvAvailableStock = findViewById(R.id.tvAvailableStock);
        tvPromoStock = findViewById(R.id.tvPromoStock);
        tvMinimumStock = findViewById(R.id.tvMinimumStock);
        tvSupplementLevel = findViewById(R.id.tvSupplementLevel);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void displayProductCode(String productCode) {
        tvProductCode.setText(productCode);  // Hiển thị mã sản phẩm
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
