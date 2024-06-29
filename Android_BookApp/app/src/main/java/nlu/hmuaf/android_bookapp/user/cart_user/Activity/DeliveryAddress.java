package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.graphics.Color;
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
        stepView = findViewById(R.id.stepViewInDeliverAddress);
        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");
//        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
//        stepView.go(1, true);
        String[] arrayString = {"1", "2", "3", "4"};
        stepView.setLabels(arrayString) // Đặt nhãn cho StepsView
                .setBarColorIndicator(Color.GRAY) // Đặt màu mặc định cho thanh chỉ báo (màu xám)
                .setProgressColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho chỉ báo tiến độ (màu xám)
                .setLabelColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho nhãn (màu xám)
                .setCompletedPosition(1) // Đặt vị trí đã hoàn thành
                .drawView(); // Vẽ StepsView
        addAddress = findViewById(R.id.buttonAddNewAddress);

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
