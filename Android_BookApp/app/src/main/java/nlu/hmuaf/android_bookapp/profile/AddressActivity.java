package nlu.hmuaf.android_bookapp.profile;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.AddressAdapter;
import nlu.hmuaf.android_bookapp.profile.Address;

public class AddressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Địa chỉ của Tôi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addressList = new ArrayList<>();
        // Load data from database (this is a mock; replace with real database query)
        loadAddresses();

        adapter = new AddressAdapter(addressList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addAddressButton).setOnClickListener(view -> {
            // Handle adding a new address
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

    private void loadAddresses() {
        // Mock data, replace with real data from your database
        addressList.add(new Address("Tường minh", "(+84) 847 881 229", "Đại Học Nông Lâm, Cư Xá E", "Phường Linh Trung, Thành Phố Thủ Đức, TP. Hồ Chí Minh", true));
        addressList.add(new Address("Tường minh", "(+84) 847 881 229", "Đại Học Nông Lâm, Cư Xá E", "Phường Linh Trung, Thành Phố Thủ Đức, TP. Hồ Chí Minh", false));
        addressList.add(new Address("Tường minh", "(+84) 847 881 229", "Đại Học Nông Lâm, Cư Xá E", "Phường Linh Trung, Thành Phố Thủ Đức, TP. Hồ Chí Minh", false));
        // Add more addresses as needed
    }
}
