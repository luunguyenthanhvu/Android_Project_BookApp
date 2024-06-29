package nlu.hmuaf.android_bookapp.admin.manage_inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nlu.hmuaf.android_bookapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvProductCode, tvSupplementLevel;
    private Spinner spinnerLocation;
    private EditText etAvailableStock, etPromoStock, etMinimumStock;
    private Button btnDecreaseAvailable, btnIncreaseAvailable;
    private Button btnDecreasePromo, btnIncreasePromo;
    private Button btnDecreaseMinimum, btnIncreaseMinimum;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi Tiết Sản Phẩm");

        // Khởi tạo các thành phần giao diện
        initializeViews();

        // Thiết lập Spinner cho vị trí kho
        setupSpinner();

        // Nhận dữ liệu từ Intent
        Book book = (Book) getIntent().getSerializableExtra("book");
        if (book != null) {
            displayBookDetails(book);
        }

        // Thiết lập sự kiện cho các nút tăng giảm
        setupButtonListeners();

        btnUpdate.setOnClickListener(v -> {
            // Logic cho nút cập nhật
            Toast.makeText(ProductDetailActivity.this, "Đã cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductDetailActivity.this, ManageInventorDetailActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initializeViews() {
        tvProductCode = findViewById(R.id.tvProductCode);
        tvSupplementLevel = findViewById(R.id.tvSupplementLevel);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        etAvailableStock = findViewById(R.id.etAvailableStock);
        etPromoStock = findViewById(R.id.etPromoStock);
        etMinimumStock = findViewById(R.id.etMinimumStock);
        btnDecreaseAvailable = findViewById(R.id.btnDecreaseAvailable);
        btnIncreaseAvailable = findViewById(R.id.btnIncreaseAvailable);
        btnDecreasePromo = findViewById(R.id.btnDecreasePromo);
        btnIncreasePromo = findViewById(R.id.btnIncreasePromo);
        btnDecreaseMinimum = findViewById(R.id.btnDecreaseMinimum);
        btnIncreaseMinimum = findViewById(R.id.btnIncreaseMinimum);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        btnDecreaseAvailable.setOnClickListener(v -> updateStock(etAvailableStock, -1));
        btnIncreaseAvailable.setOnClickListener(v -> updateStock(etAvailableStock, 1));
        btnDecreasePromo.setOnClickListener(v -> updateStock(etPromoStock, -1));
        btnIncreasePromo.setOnClickListener(v -> updateStock(etPromoStock, 1));
        btnDecreaseMinimum.setOnClickListener(v -> updateStock(etMinimumStock, -1));
        btnIncreaseMinimum.setOnClickListener(v -> updateStock(etMinimumStock, 1));
    }

    private void updateStock(EditText editText, int change) {
        int currentValue;
        try {
            currentValue = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            currentValue = 0;
        }
        int newValue = currentValue + change;
        if (newValue >= 0) {
            editText.setText(String.valueOf(newValue));
        }
    }

    private void displayBookDetails(Book book) {
        tvProductCode.setText(book.getBookID());
        tvSupplementLevel.setText(book.getStatus());
        etAvailableStock.setText(String.valueOf(book.getQuantity()));
        // Assuming location is stored in book, set it in spinner
        if (book.getStorageLocation() != null) {
            int spinnerPosition = ((ArrayAdapter) spinnerLocation.getAdapter()).getPosition(book.getStorageLocation());
            spinnerLocation.setSelection(spinnerPosition);
        }
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
