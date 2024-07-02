package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.user.profile.adapter.AddressAdapter;
import nlu.hmuaf.android_bookapp.user.profile.classess.Address;
import nlu.hmuaf.android_bookapp.user.profile.classess.User;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private static final int EDIT_ADDRESS_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private List<ListAddressResponseDTO> addressList;
    private BookAppApi bookAppApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_address);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Địa chỉ của Tôi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addressList = new ArrayList<>();
        adapter = new AddressAdapter(this, addressList);
        recyclerView.setAdapter(adapter);

        getAddressUser();

        findViewById(R.id.addAddressButton).setOnClickListener(view -> {
            Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
            startActivityForResult(intent, EDIT_ADDRESS_REQUEST_CODE);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int findAddressIndexById(int addressId) {
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).getAddressId() == addressId) {
                return i;
            }
        }
        return -1;
    }

    public void getAddressUser() {
        bookAppApi = BookAppService.getClient(MyUtils.getTokenResponse(getApplicationContext()).getToken());
        Call<List<ListAddressResponseDTO>> call = bookAppApi.getUserAddress(MyUtils.getTokenResponse(getApplicationContext()).getUserId());
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<ListAddressResponseDTO> responseDTOS = response.body();
                    adapter.updateData(responseDTOS);
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable throwable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ADDRESS_REQUEST_CODE && resultCode == RESULT_OK) {
            getAddressUser(); // Lấy lại dữ liệu mới từ server
        }
    }
}
