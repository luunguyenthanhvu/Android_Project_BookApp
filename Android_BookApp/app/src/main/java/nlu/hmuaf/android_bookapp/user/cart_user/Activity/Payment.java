package nlu.hmuaf.android_bookapp.user.cart_user.activity;

import android.content.Intent;
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
    private String paymentMethod="";
    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        toolbar = findViewById(R.id.toolbarPayment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        stepView = findViewById(R.id.stepViewInPayment);
        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");
//        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
//        stepView.go(2, true);

        stepView.setLabels((String[]) listStepView.toArray()) // Đặt các bước (labels) cho StepsView từ listStepView
                .setBarColorIndicator(getApplicationContext().getColor(android.R.color.darker_gray)) // Đặt màu của thanh chỉ báo
                .setProgressColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo tiến độ
                .setLabelColorIndicator(getApplicationContext().getColor(android.R.color.black)) // Đặt màu của chỉ báo nhãn
                .setCompletedPosition(2) // Đặt vị trí đã hoàn thành (nếu cần, ví dụ: 2)
                .drawView();
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
                List<Books> listBook = (ArrayList<Books>)getIntent().getSerializableExtra("listBookChoose");
                HashMap<Integer,Integer> quantityMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("selectedQuantities");
                Address address = (Address) getIntent().getSerializableExtra("address");
                intent.putExtra("listBook", (ArrayList<Books>) listBook);
                intent.putExtra("quantityMap", quantityMap);
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
