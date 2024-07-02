package nlu.hmuaf.android_bookapp.Admin.ManageProduct;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nlu.hmuaf.android_bookapp.Admin.ManageInventory.AddBookActivity;
import nlu.hmuaf.android_bookapp.R;

public class ManageProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_product);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Tạo danh sách sản phẩm mẫu
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop A", "Đã đăng bán", "86.000 VNĐ", "Lăng c", "Sản phẩm A"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop B", "Ẩn", "24.000 VNĐ", "Long c", "Sản phẩm B"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop A", "Đã đăng bán", "86.000 VNĐ", "Lăng c", "Sản phẩm A"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop B", "Ẩn", "24.000 VNĐ", "Long c", "Sản phẩm B"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop A", "Đã đăng bán", "86.000 VNĐ", "Lăng c", "Sản phẩm A"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop B", "Ẩn", "24.000 VNĐ", "Long c", "Sản phẩm B"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop A", "Đã đăng bán", "86.000 VNĐ", "Lăng c", "Sản phẩm A"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop B", "Ẩn", "24.000 VNĐ", "Long c", "Sản phẩm B"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop A", "Đã đăng bán", "86.000 VNĐ", "Lăng c", "Sản phẩm A"));
        productList.add(new Product("https://salt.tikicdn.com/cache/w300/ts/product/04/84/de/17a3741b4d0677d06e7f5570cee6e603.jpg", "Shop B", "Ẩn", "24.000 VNĐ", "Long c", "Sản phẩm B"));

        for (Product product : productList) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_VERTICAL); // Canh các thành phần theo chiều dọc

            // ImageView cho hình ảnh sản phẩm
            ImageView imageView = new ImageView(this);
            TableRow.LayoutParams imageLayoutParams = new TableRow.LayoutParams(400, 400); // Kích thước ảnh là 400x400 pixel
            imageView.setLayoutParams(imageLayoutParams);
            // Sử dụng Glide để tải hình ảnh từ URL
            Glide.with(this).load(product.getImage()).into(imageView);
            row.addView(imageView);

            // TextView cho tên shop
            TextView shopTextView = new TextView(this);
            shopTextView.setText(product.getPublishCompany());
            TableRow.LayoutParams shopParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            shopTextView.setLayoutParams(shopParams);
            shopTextView.setGravity(Gravity.CENTER_HORIZONTAL); // Canh giữa nội dung theo chiều dọc
            row.addView(shopTextView);

            // TextView cho trạng thái
            TextView statusTextView = new TextView(this);
            statusTextView.setText(product.getStatus());
            TableRow.LayoutParams statusParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            statusParams.width = getResources().getDimensionPixelSize(R.dimen.column_width_status); // Sử dụng kích thước từ resources
            statusTextView.setLayoutParams(statusParams);
            statusTextView.setGravity(Gravity.CENTER_HORIZONTAL); // Canh giữa nội dung theo chiều dọc
            row.addView(statusTextView);

            // TextView cho ngày tạo
            TextView dateCreateTextView = new TextView(this);
            dateCreateTextView.setText(product.getPrice());
            TableRow.LayoutParams dateCreateParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            dateCreateParams.width = getResources().getDimensionPixelSize(R.dimen.column_width_date_create); // Sử dụng kích thước từ resources
            dateCreateTextView.setLayoutParams(dateCreateParams);
            dateCreateTextView.setGravity(Gravity.CENTER_HORIZONTAL); // Canh giữa nội dung theo chiều dọc
            row.addView(dateCreateTextView);


            TextView dateEditTextView = new TextView(this);
            dateEditTextView.setText(product.getAuthorName());
            TableRow.LayoutParams dateEditParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            dateEditParams.width = getResources().getDimensionPixelSize(R.dimen.column_width_date_edit); // Sử dụng kích thước từ resources
            dateEditTextView.setLayoutParams(dateEditParams);
            dateEditTextView.setGravity(Gravity.CENTER_HORIZONTAL); // Canh giữa nội dung theo chiều dọc
            row.addView(dateEditTextView);

            // TextView cho tên sản phẩm
            TextView productNameTextView = new TextView(this);
            productNameTextView.setText(product.getProductName());
            TableRow.LayoutParams productNameParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            productNameParams.width = getResources().getDimensionPixelSize(R.dimen.column_width_product_name); // Sử dụng kích thước từ resources
            productNameTextView.setLayoutParams(productNameParams);
            productNameTextView.setGravity(Gravity.CENTER_HORIZONTAL); // Canh giữa nội dung theo chiều dọc
            row.addView(productNameTextView);

            // ImageButton cho thao tác (ví dụ: chỉnh sửa, xóa)
            ImageButton actionButton = new ImageButton(this);
            actionButton.setImageResource(R.drawable.edit); // Thay thế bằng icon thực tế cho chỉnh sửa
            actionButton.setBackgroundResource(android.R.drawable.btn_default);
            actionButton.setId(productList.indexOf(product));

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int productId = v.getId(); // Lấy ID của ImageButton được nhấn
                    Product selectedProduct = productList.get(productId); // Lấy sản phẩm tương ứng từ danh sách productList

                    // Mở Activity chỉnh sửa sản phẩm
                    // Ví dụ: Mở một activity mới để chỉnh sửa thông tin sản phẩm
                    Intent intent = new Intent(ManageProductActivity.this, AddBookActivity.class);
//                    intent.putExtra("product", (Parcelable) selectedProduct); // Truyền thông tin sản phẩm cần chỉnh sửa
                    startActivity(intent);
                }

            });
            row.addView(actionButton);

            tableLayout.addView(row);

            View divider = new View(this);
            TableRow.LayoutParams dividerParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1); // Chiều cao của đường gạch là 1 pixel
            divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Màu sắc của đường gạch
            divider.setLayoutParams(dividerParams);
            tableLayout.addView(divider);
        }
    }
}