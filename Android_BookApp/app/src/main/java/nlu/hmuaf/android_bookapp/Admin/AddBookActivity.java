package nlu.hmuaf.android_bookapp.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;

public class AddBookActivity extends AppCompatActivity {

    private static final int PICK_THUMBNAIL_REQUEST = 1;
    private static final int PICK_IMAGES_REQUEST = 2;
    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 101;
    private static final int PERMISSIONS_REQUEST_READ_IMAGES = 102;

    private EditText etBookTitle, etBookID, etBookDescription, etBookPrice, etNumberOfPages, etPublicationDate, etLanguage, etSize, etFormat, etAuthor, etDiscountCode;
    private Button btnSelectPublishers, btnUploadThumbnail, btnUploadBookImages, btnSave, btnCancel;
    private ImageView ivThumbnail;
    private RecyclerView rvBookImages;
    private BookImagesAdapter adapter;
    private List<Uri> imageUris = new ArrayList<>();
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
                openFileChooser(false, PICK_THUMBNAIL_REQUEST);
            } else {
                requestStoragePermission();
            }
        });
        btnUploadBookImages.setOnClickListener(v -> {
            if (checkPermission()) {
                openFileChooser(true, PICK_IMAGES_REQUEST);
            } else {
                requestStoragePermission();
            }
        });
        btnSave.setOnClickListener(v -> saveBook());
        btnCancel.setOnClickListener(v -> finish());

        rvBookImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new BookImagesAdapter(this, imageUris);
        rvBookImages.setAdapter(adapter);
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
        ivThumbnail = findViewById(R.id.ivThumbnail);
        rvBookImages = findViewById(R.id.rvBookImages);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                new AlertDialog.Builder(this)
                        .setTitle("Yêu cầu quyền")
                        .setMessage("Ứng dụng cần quyền truy cập hình ảnh để tải ảnh.")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSIONS_REQUEST_READ_IMAGES))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSIONS_REQUEST_READ_IMAGES);
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Yêu cầu quyền")
                        .setMessage("Ứng dụng cần quyền truy cập bộ nhớ để tải ảnh.")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_STORAGE))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_STORAGE || requestCode == PERMISSIONS_REQUEST_READ_IMAGES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền truy cập đã được cấp!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền truy cập bị từ chối. Không thể truy cập hình ảnh.", Toast.LENGTH_SHORT).show();
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

    private void openFileChooser(boolean allowMultiple, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_THUMBNAIL_REQUEST) {
                // Handle single image selection (thumbnail)
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    Glide.with(this).load(selectedImageUri).into(ivThumbnail);
                }
            } else if (requestCode == PICK_IMAGES_REQUEST) {
                // Handle multiple image selection
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                    }
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
                }
                adapter.notifyDataSetChanged();
            }
        }
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
