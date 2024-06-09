package nlu.hmuaf.android_bookapp.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.AlertDialog;
import java.util.ArrayList;

import nlu.hmuaf.android_bookapp.R;

public class AddBookActivity extends AppCompatActivity {

    private static final int PICK_THUMBNAIL_REQUEST = 1;
    private static final int PICK_IMAGES_REQUEST = 2;
    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 101;

    private EditText etBookTitle, etBookID, etBookDescription, etBookPrice, etNumberOfPages, etPublicationDate, etLanguage, etSize, etFormat, etAuthor, etDiscountCode;
    private Button btnSelectPublishers, btnUploadThumbnail, btnUploadBookImages, btnSave, btnCancel;
    private ArrayList<String> selectedPublishers = new ArrayList<>();
    private String[] publisherList = {"Yen On", "A", "B", "C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm Sách");

        // Initialize all views
        initializeViews();

        btnSelectPublishers.setOnClickListener(v -> showPublisherDialog());
        btnUploadThumbnail.setOnClickListener(v -> {
            if (checkPermission()) {
                openFileChooser(false);
            } else {
                requestStoragePermission();
            }
        });
        btnUploadBookImages.setOnClickListener(v -> {
            if (checkPermission()) {
                openFileChooser(true);
            } else {
                requestStoragePermission();
            }
        });
        btnSave.setOnClickListener(v -> saveBook());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        etBookTitle = findViewById(R.id.etBookTitle);
        etBookID = findViewById(R.id.etBookID);
        etBookDescription = findViewById(R.id.etBookDescription);
        etBookPrice = findViewById(R.id.etBookPrice);
        etNumberOfPages = findViewById(R.id.etNumberOfPages);
        etPublicationDate = findViewById(R.id.etPublicationDate);
        etLanguage = findViewById(R.id.etLanguage);
        etSize = findViewById(R.id.etSize);
        etFormat = findViewById(R.id.etFormat);
        etAuthor = findViewById(R.id.etAuthor);
        etDiscountCode = findViewById(R.id.etDiscountCode);
        btnSelectPublishers = findViewById(R.id.btnSelectPublishers);
        btnUploadThumbnail = findViewById(R.id.btnUploadThumbnail);
        btnUploadBookImages = findViewById(R.id.btnUploadBookImages);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Permission needed to access photos for uploading.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Cannot access images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPublisherDialog() {
        boolean[] checkedItems = new boolean[publisherList.length]; // Temporary array to store checked items for dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
        builder.setTitle("Chọn nhà xuất bản");
        builder.setMultiChoiceItems(publisherList, checkedItems, (dialog, which, isChecked) -> {
            String publisher = publisherList[which];
            if (isChecked) {
                if (!selectedPublishers.contains(publisher)) {
                    selectedPublishers.add(publisher);
                }
            } else {
                selectedPublishers.remove(publisher);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            Toast.makeText(AddBookActivity.this, "Publishers Selected: " + selectedPublishers, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openFileChooser(boolean allowMultiple) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), allowMultiple ? PICK_IMAGES_REQUEST : PICK_THUMBNAIL_REQUEST);
    }

    private void saveBook() {
        String title = etBookTitle.getText().toString();
        String authors = etAuthor.getText().toString();

        // Assuming you are saving the details to a local database or sending them to a server:
        saveBookDetails(title, authors, selectedPublishers); // Implement this method based on your backend
        Toast.makeText(this, "Sách đã được lưu cùng với các nhà xuất bản đã chọn.", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
    }

    private void saveBookDetails(String title, String authors, ArrayList<String> publishers) {
        // Implementation of how you save these details to your backend or database
    }
}
