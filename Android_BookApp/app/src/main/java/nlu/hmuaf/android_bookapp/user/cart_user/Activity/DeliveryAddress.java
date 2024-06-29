package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Books;
import nlu.hmuaf.android_bookapp.user.cart_user.dialogs.AddNewAddressFromUserDialog;
import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.FragmentListAddressUser;
import nlu.hmuaf.android_bookapp.R;

public class DeliveryAddress extends AppCompatActivity implements AddNewAddressFromUserDialog.OnAddressAddedListener, FragmentListAddressUser.OnAddressSelectedListenerFragment {
    private Toolbar toolbar;
    private StepsView stepView;
    private Button addAddress;
    private Button addAddressByGoogleMap;

    private Button nextStep;
    private List<String> listStepView = new ArrayList<>();
    private List<Address> addressList = new ArrayList<>();
    private Address selectedAddress = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_address);

        toolbar = findViewById(R.id.toolbarDeliveryAddress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        stepView = findViewById(R.id.stepViewInDeliverAddress);
        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");
//        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
//        stepView.go(1, true);
        stepView.setLabels((String[]) listStepView.toArray()) // Đặt các bước (labels) cho StepsView từ listStepView
                .setBarColorIndicator(getApplicationContext().getColor(android.R.color.darker_gray)) // Đặt màu của thanh chỉ báo
                .setProgressColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo tiến độ
                .setLabelColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo nhãn
                .setCompletedPosition(3) // Đặt vị trí đã hoàn thành (nếu cần, ví dụ: 3)
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
                AddNewAddressFromUserDialog dialog = new AddNewAddressFromUserDialog(DeliveryAddress.this, DeliveryAddress.this);
                dialog.show();


            }
        });
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Books> listBook = (ArrayList<Books>) getIntent().getSerializableExtra("listBookChoose");
                HashMap<Integer, Integer> quantityMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("selectedQuantities");
                Intent intent = new Intent(DeliveryAddress.this, Payment.class);
                intent.putExtra("listBookChoose", (ArrayList<Books>) listBook);
                intent.putExtra("selectedQuantities", quantityMap);
                intent.putExtra("address", selectedAddress);
                startActivity(intent);
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
    public void onAddressAdded(Address address) {


        // Lấy fragment và cập nhật adapter
        FragmentListAddressUser fragment = (FragmentListAddressUser) getSupportFragmentManager().findFragmentById(R.id.contentListAddress);
        if (fragment != null) {
            fragment.updateAddressList(address);
        }
    }

    @Override
    public void onAddressSelectedFragment(Address address) {
        this.selectedAddress = address;
    }
}
