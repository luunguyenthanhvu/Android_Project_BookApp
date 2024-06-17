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
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.user.profile.adapter.AddressAdapter;
import nlu.hmuaf.android_bookapp.user.profile.Class.Address;
import nlu.hmuaf.android_bookapp.user.profile.Class.User;

public class AddressActivity extends AppCompatActivity {

    private static final int EDIT_ADDRESS_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private List<Address> addressList;

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
        loadAddresses();

        adapter = new AddressAdapter(this, addressList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addAddressButton).setOnClickListener(view -> {
            Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
            intent.putExtra("addressList", new ArrayList<>(addressList)); // Truyền danh sách địa chỉ
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ADDRESS_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Address updatedAddress = (Address) data.getSerializableExtra("updatedAddress");
            updateAddressList(updatedAddress);
        }
    }

    private void updateAddressList(Address updatedAddress) {
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).getAddressId() == updatedAddress.getAddressId()) {
                addressList.set(i, updatedAddress);
                adapter.notifyItemChanged(i);
            } else if (updatedAddress.isDefault()) {
                addressList.get(i).setDefault(false);
                adapter.notifyItemChanged(i);
            }
        }
    }

    private void loadAddresses() {
        User user1 = new User(1, 1, "tuongminh", "password", "hash", "2023-01-01");
        user1.setFirstName("Tường");
        user1.setLastName("Minh");
        user1.setPhoneNum("1234567");

        Address address1 = new Address(1, "TP. Hồ Chí Minh", "Thủ Đức", "Linh Trung", "Đại Học Nông Lâm, Cư Xá E", true);
        address1.setUser(user1);
        addressList.add(address1);

        Address address2 = new Address(1, "TP. Hồ Chí Minh", "Thủ Đức", "Linh Trung", "Đại Học Nông Lâm, Cư Xá E", false);
        address2.setUser(user1);
        addressList.add(address2);

        Address address3 = new Address(1, "TP. Hồ Chí Minh", "Thủ Đức", "Linh Trung", "Đại Học Nông Lâm, Cư Xá E", false);
        address3.setUser(user1);
        addressList.add(address3);
    }
}
