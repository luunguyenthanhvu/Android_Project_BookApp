package nlu.hmuaf.android_bookapp.admin.manage_inventory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nlu.hmuaf.android_bookapp.R;

public class BulkUpdateActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;

    private LinearLayout fileListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_bulk_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cập Nhật Hàng Loạt");

        Button btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(view -> openFilePicker());

        fileListLayout = findViewById(R.id.fileListLayout);

        Button btnComplete = findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(view -> {
            // Handle complete action here
            Toast.makeText(BulkUpdateActivity.this, "Đã cập nhật các sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String fileNameStr = getFileName(uri);
                String fileTypeStr = getFileType(fileNameStr);
                String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                addFileToList(currentDate, fileTypeStr, fileNameStr, "Chưa xử lý", "Đang chờ xử lý");
            }
        }
    }

    private void addFileToList(String date, String fileType, String fileName, String processed, String activity) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View fileItemView = inflater.inflate(R.layout.admin_file_item_layout, fileListLayout, false);

        TextView fileDate = fileItemView.findViewById(R.id.fileDate);
        TextView fileTypeView = fileItemView.findViewById(R.id.fileType);
        TextView fileNameView = fileItemView.findViewById(R.id.fileName);
        TextView fileProcessed = fileItemView.findViewById(R.id.fileProcessed);
        TextView fileActivity = fileItemView.findViewById(R.id.fileActivity);

        fileDate.setText(date);
        fileTypeView.setText(fileType);
        fileNameView.setText(fileName);
        fileProcessed.setText(processed);
        fileActivity.setText(activity);

        fileListLayout.addView(fileItemView, 0); // Add the new file item view at the top
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                    result = cursor.getString(nameIndex);
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String getFileType(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot != -1) {
            return fileName.substring(lastDot + 1);
        }
        return "Unknown";
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
