package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.shuhart.stepview.StepView;

import com.anton46.stepsview.StepsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.api_google_map.GoogleMapActivity;
import nlu.hmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Books;
import nlu.hmuaf.android_bookapp.user.cart_user.dialogs.AddNewAddressFromUserDialog;
import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.FragmentListAddressUser;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryAddress extends AppCompatActivity implements FragmentListAddressUser.OnAddressSelectedListenerFragment {
    private Toolbar toolbar;
    private StepsView stepView;
    private Button addAddress;
    private Button addAddressByGoogleMap;
    private Button nextStep;
    private List<String> listStepView = new ArrayList<>();
    private ListAddressResponseDTO selectedAddress;
    private BookAppApi bookAppApi;
    private FragmentListAddressUser fragmentListAddressUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_address);

        fragmentListAddressUser = new FragmentListAddressUser();
        toolbar = findViewById(R.id.toolbarDeliveryAddress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stepView = findViewById(R.id.stepViewInDeliverAddress);
        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");

        String[] arrayString = {"Bước 1", "Bước 2", "Bước 3", "Bước 4"};
        stepView.setLabels(arrayString) // Đặt nhãn cho StepsView
                .setBarColorIndicator(Color.parseColor("#E8E4E9")) // Đặt màu mặc định cho thanh chỉ báo (màu xám)
                .setProgressColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho chỉ báo tiến độ (màu xám)
                .setLabelColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho nhãn (màu xám)
                .setCompletedPosition(1) // Đặt vị trí đã hoàn thành
                .drawView(); // Vẽ StepsView
        addAddress = findViewById(R.id.buttonAddNewAddress);
        addAddressByGoogleMap = findViewById(R.id.buttonAddAdressByGoogleMap);
        nextStep = findViewById(R.id.buttonDeliverToThisAddress);

        // Tạo instance của Fragment
        FragmentListAddressUser fragmentListAddressUser = new FragmentListAddressUser();
        fragmentListAddressUser.setOnAddressSelectedListener(this);

        // Lấy FragmentManager và bắt đầu transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thêm Fragment vào FrameLayout
        fragmentTransaction.add(R.id.contentListAddress, fragmentListAddressUser);
        fragmentTransaction.commit();


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAddressFromUserDialog dialog = new AddNewAddressFromUserDialog(DeliveryAddress.this, fragmentListAddressUser);
                dialog.show();
            }
        });
        addAddressByGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddress.this, GoogleMapActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartItems> listBook = (ArrayList<CartItems>) getIntent().getSerializableExtra("listBookChoose");

                // Kiểm tra xem người dùng đã chọn địa chỉ hay chưa
                if (selectedAddress == null) {
                    // Nếu chưa chọn địa chỉ, hiển thị thông báo cho người dùng
                    Toast.makeText(DeliveryAddress.this, "Please select a delivery address.", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu đã chọn địa chỉ, chuyển sang màn hình Payment
                    Intent intent = new Intent(DeliveryAddress.this, Payment.class);
                    intent.putExtra("listBookChoose", (ArrayList<CartItems>) listBook);
                    intent.putExtra("address", selectedAddress);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Kết thúc activity hiện tại và quay lại màn hình trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAddressSelectedFragment(ListAddressResponseDTO address) {
        this.selectedAddress = address;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String address = data.getStringExtra("detailAddress"); // Retrieve address from intent
                if (address != null && !address.isEmpty()) {
//                    Toast.makeText(this, "Address from Google Map: " + address, Toast.LENGTH_SHORT).show();
                    TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(this);
                    bookAppApi = BookAppService.getClient();

                    AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder()
                            .addressDetails(address)
                            .mainAddress(false)
                            .build();

                    Call<List<ListAddressResponseDTO>> call = bookAppApi.addNewAddress(tokenResponseDTO.getUserId(), addressRequestDTO);

                    call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
                        @Override
                        public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                            if (response.isSuccessful()) {
                                List<ListAddressResponseDTO> addresses = response.body();
                                renderData(addresses);
                            } else {
                                // Xử lý lỗi ở đây
                                System.out.println("Thêm địa chỉ thất bại: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable t) {
                            // Xử lý lỗi khi gọi API thất bại
                            t.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    private void renderData(List<ListAddressResponseDTO> listAddressDTO) {
        FragmentListAddressUser fragment = (FragmentListAddressUser) getSupportFragmentManager().findFragmentById(R.id.contentListAddress);
        fragment.updateData(listAddressDTO);
    }

    public void getUserAddress() {
        bookAppApi = BookAppService.getClient(MyUtils.getTokenResponse(getApplicationContext()).getToken());
        Call<List<ListAddressResponseDTO>> call = bookAppApi.getUserAddress(MyUtils.getTokenResponse(getApplicationContext()).getUserId());
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    fragmentListAddressUser.updateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable throwable) {

            }
        });
    }

}
