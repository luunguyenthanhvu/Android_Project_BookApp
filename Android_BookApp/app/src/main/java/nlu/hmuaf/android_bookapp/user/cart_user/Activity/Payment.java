package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.shuhart.stepview.StepView;

import com.anton46.stepsview.StepsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Books;
import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.BankCardFragment;
import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.CreditCardFragment;
import nlu.hmuaf.android_bookapp.R;

public class Payment extends AppCompatActivity {
    private Toolbar toolbar;
    private StepsView stepView;
    private RadioGroup radioGroupPaymentMethods;

    private Button payNow;

    private List<String> listStepView = new ArrayList<>();
    private String paymentMethod = "";
    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        toolbar = findViewById(R.id.toolbarPayment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stepView = findViewById(R.id.stepViewInPayment);
        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");


        String[] arrayString = {"Bước 1", "Bước 2", "Bước 3", "Bước 4"};
        stepView.setLabels(arrayString) // Đặt nhãn cho StepsView
                .setBarColorIndicator(Color.parseColor("#E8E4E9")) // Đặt màu mặc định cho thanh chỉ báo (màu xám)
                .setProgressColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho chỉ báo tiến độ (màu xám)
                .setLabelColorIndicator(Color.parseColor("#B868E9")) // Đặt màu mặc định cho nhãn (màu xám)
                .setCompletedPosition(2) // Đặt vị trí đã hoàn thành
                .drawView(); // Vẽ StepsView
        radioGroupPaymentMethods = findViewById(R.id.radioGroupPaymentMethods);
        payNow = findViewById(R.id.buttonPayNow);

        radioGroupPaymentMethods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment selectedFragment = null;

                if (checkedId == R.id.radioButton_1) {
                    paymentMethod = "Thanh toán khi nhận hàng";
                } else if (checkedId == R.id.radioButton_3) {
                    paymentMethod = "MoMo";
                } else if (checkedId == R.id.radioButton_2) {
                    paymentMethod = "Thẻ ngân hàng";
                    selectedFragment = new BankCardFragment();
                } else if (checkedId == R.id.radioButton_4) {
                    paymentMethod = "Thẻ tín dụng";
                    selectedFragment = new CreditCardFragment();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (selectedFragment != null) {
                    fragmentTransaction.replace(R.id.fragmentContainer, selectedFragment);
                } else {
                    Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
                    if (currentFragment != null) {
                        fragmentTransaction.remove(currentFragment);
                    }
                }

                fragmentTransaction.commit();
            }
        });


        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, OrderSummary.class);
                if (selectedFragment instanceof BankCardFragment) {
                    BankCardFragment bankCardFragment = (BankCardFragment) selectedFragment;
                    intent.putExtra("cardNumber", bankCardFragment.getBankCardNumber());
                    intent.putExtra("cardHolderName", bankCardFragment.getBankCardHolderName());
                    intent.putExtra("cardBankName", bankCardFragment.getBankCardName());
                } else if (selectedFragment instanceof CreditCardFragment) {
                    CreditCardFragment creditCardFragment = (CreditCardFragment) selectedFragment;
                    intent.putExtra("cardNumber", creditCardFragment.getCreditCardNumber());
                    intent.putExtra("cardHolderName", creditCardFragment.getCreditCardHolderName());

                }
                List<CartItems> listBook = (ArrayList<CartItems>) getIntent().getSerializableExtra("listBookChoose");
                Address address = (Address) getIntent().getSerializableExtra("address");
                intent.putExtra("listBook", (ArrayList<CartItems>) listBook);
                intent.putExtra("address", address);
                intent.putExtra("paymentMethod", paymentMethod);
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
}
