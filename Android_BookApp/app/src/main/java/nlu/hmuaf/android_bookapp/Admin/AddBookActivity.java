package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.FileNotFoundException;
import java.io.InputStream;

import nlu.hmuaf.android_bookapp.R;

public class AddBookActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etBookTitle, etBookDescription, etBookPrice;
    private EditText etNumberOfPages, etPublicationDate, etLanguage, etGenre;
    private EditText etPublisherName, etAuthor, etDiscountCode;
    private ImageView ivBookImage;
    private Button btnUploadImage, btnSave, btnCancel;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm Sách");

        etBookTitle = findViewById(R.id.etBookTitle);
        etBookDescription = findViewById(R.id.etBookDescription);
        etBookPrice = findViewById(R.id.etBookPrice);
        etNumberOfPages = findViewById(R.id.etNumberOfPages);
        etPublicationDate = findViewById(R.id.etPublicationDate);
        etLanguage = findViewById(R.id.etLanguage);
        etGenre = findViewById(R.id.etGenre);
        etPublisherName = findViewById(R.id.etPublisherName);
        ivBookImage = findViewById(R.id.ivBookImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        etAuthor = findViewById(R.id.etAuthor);
        etDiscountCode = findViewById(R.id.etDiscountCode);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivBookImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveBook() {
        // Lấy dữ liệu từ các trường nhập liệu
        String title = etBookTitle.getText().toString();
        String description = etBookDescription.getText().toString();
        String price = etBookPrice.getText().toString();
        String numberOfPages = etNumberOfPages.getText().toString();
        String publicationDate = etPublicationDate.getText().toString();
        String language = etLanguage.getText().toString();
        String genre = etGenre.getText().toString();
        String publisherName = etPublisherName.getText().toString();
        String author = etAuthor.getText().toString();
        String discountCode = etDiscountCode.getText().toString();

        // Xử lý dữ liệu và lưu vào cơ sở dữ liệu (API, SQLite, v.v.)
        // Ví dụ sử dụng Retrofit để gọi API lưu sách

        // Nếu lưu thành công, thông báo cho người dùng và kết thúc activity
        Toast.makeText(this, "Sách đã được lưu", Toast.LENGTH_SHORT).show();
        finish();
    }
}
