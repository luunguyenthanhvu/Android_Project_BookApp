package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.profile.classess.Address;
import nlu.hmuaf.android_bookapp.user.profile.classess.User;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity {
    private EditText addressText;
    private Switch defaultSwitch;
    private ListAddressResponseDTO address;
    private boolean isNew = true;
    private BookAppApi bookAppApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        addressText = findViewById(R.id.addressText);
        defaultSwitch = findViewById(R.id.defaultSwitch);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button saveButton = findViewById(R.id.saveButton);

        address = (ListAddressResponseDTO) getIntent().getSerializableExtra("address");
        isNew = (address == null);

        if (!isNew) {
            addressText.setText(address.getAddressDetails());
            defaultSwitch.setChecked(address.isMainAddress());
        } else {
            address = new ListAddressResponseDTO();
            defaultSwitch.setChecked(false);
        }

        saveButton.setOnClickListener(v -> {
            if (validateInput()) {
                TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(getApplicationContext());
                address.setMainAddress(defaultSwitch.isChecked());
                address.setAddressDetails(addressText.getText().toString().trim());
                bookAppApi = BookAppService.getClient(tokenResponseDTO.getToken());

                AddressRequestDTO requestDTO = AddressRequestDTO
                        .builder()
                        .addressDetails(address.getAddressDetails())
                        .mainAddress(address.isMainAddress())
                        .build();

                if (isNew) {
                    addNewAddress(tokenResponseDTO, requestDTO);
                } else {
                    requestDTO.setAddressId(address.getAddressId());
                    updateUserAddress(tokenResponseDTO, requestDTO);
                }

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent); // Thiết lập kết quả
                finish(); // Kết thúc Activity
            }
        });


        deleteButton.setVisibility(isNew ? View.GONE : View.VISIBLE);
        deleteButton.setOnClickListener(v -> showConfirmDialog());
    }


    private void updateUserAddress(TokenResponseDTO tokenResponseDTO, AddressRequestDTO requestDTO) {
        Call<List<ListAddressResponseDTO>> call = bookAppApi.updateUserAddress(tokenResponseDTO.getUserId(), requestDTO);
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Chỉnh sửa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable throwable) {

            }
        });
    }

    private void addNewAddress(TokenResponseDTO tokenResponseDTO, AddressRequestDTO requestDTO) {
        Call<List<ListAddressResponseDTO>> call = bookAppApi.addNewAddress(tokenResponseDTO.getUserId(), requestDTO);
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable throwable) {

            }
        });
    }

    private boolean validateInput() {
        if (addressText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập vào địa chỉ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmDialog() {
        // Tạo một AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");

        // Thiết lập nút "Có" và hành động khi người dùng chọn
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hành động xóa
                deleteItem();
                Toast.makeText(getApplicationContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập nút "Không" và hành động khi người dùng chọn
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem() {
        TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(getApplicationContext());
        bookAppApi = BookAppService.getClient(tokenResponseDTO.getToken());
        Call<List<ListAddressResponseDTO>> call = bookAppApi.deleteUserAddress(tokenResponseDTO.getUserId(), address.getAddressId());
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // Thiết lập kết quả
                    finish(); // Kết thúc Activity
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable throwable) {
                // Xử lý lỗi
            }
        });
    }


}
