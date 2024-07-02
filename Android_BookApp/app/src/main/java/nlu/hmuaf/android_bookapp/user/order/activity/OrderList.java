package nlu.hmuaf.android_bookapp.user.order.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListOrderResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.order.adapter.OrderListAdapter;
import nlu.hmuaf.android_bookapp.user.order.bean.Order;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderList extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private OrderListAdapter ordersAdapter;
    private ImageView arrowLeftIcon, searchIcon;
    private EditText searchEditText;
    private BookAppApi bookAppApi;
    List<ListOrderResponseDTO> responseDTOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_list);

        // Xử lý sự kiện nhấn vào nút quay lại
        arrowLeftIcon = findViewById(R.id.arrowleft);
        arrowLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                onBackPressed();
            }
        });
        getOrderData();
        tabLayout = findViewById(R.id.tab_layout);

        // thanh recyclerView chứa các đơn hàng
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Danh sách tất cả đơn hàng
//        allOrders = getOrdersForTab(0);

        ordersAdapter = new OrderListAdapter(responseDTOList, this);
        recyclerView.setAdapter(ordersAdapter);

        // thanh tabLayout chứa các trạng thái của đơn hàng
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        // Các tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Tất cả (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Chờ xác nhận (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Chờ lấy hàng (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang giao (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã hủy (0)"));
        tabLayout.addTab(tabLayout.newTab().setText("Hết hàng (0)"));

        // Cập nhật tiêu đề tab với số lượng đơn hàng tương ứng
        updateTabTitles();
    }

    public void updateTabTitles() {
        tabLayout.getTabAt(0).setText("Tất cả (" + responseDTOList.size() + ")");
        tabLayout.getTabAt(1).setText("Chờ xác nhận (" + responseDTOList.size() + ")");
        tabLayout.getTabAt(2).setText("Chờ lấy hàng (" + 0 + ")");
        tabLayout.getTabAt(3).setText("Đang giao (" + 0 + ")");
        tabLayout.getTabAt(4).setText("Đã giao (" + 0 + ")");
        tabLayout.getTabAt(5).setText("Đã hủy (" + 0 + ")");
        tabLayout.getTabAt(6).setText("Hết hàng (" + 0 + ")");
    }

    private void getOrderData() {
        TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(getApplicationContext());
        bookAppApi = BookAppService.getClient(tokenResponseDTO.getToken());
        Call<List<ListOrderResponseDTO>> call = bookAppApi.getUserListOrder(tokenResponseDTO.getUserId());
        call.enqueue(new Callback<List<ListOrderResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListOrderResponseDTO>> call, Response<List<ListOrderResponseDTO>> response) {
                if (response.isSuccessful()) {
                    responseDTOList = response.body();
                    ordersAdapter.updateData(responseDTOList);
                    updateTabTitles();
                }
            }

            @Override
            public void onFailure(Call<List<ListOrderResponseDTO>> call, Throwable throwable) {

            }
        });
    }
}