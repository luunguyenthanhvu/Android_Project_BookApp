package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddress extends AppCompatActivity {
    private Toolbar toolbar;
    private StepView stepView;
    private Button addAddress;
    private Button chooseAddress;
    private TextView address;
    private Button editAddress;
    private Button nextStep;
    private List<String> listStepView = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_address);

        toolbar = findViewById(R.id.toolbarDeliveryAddress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         stepView = findViewById(R.id.stepViewInDeliverAddress);
        listStepView.add("Cart");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");
        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
        stepView.go(0, true);
        addAddress = findViewById(R.id.buttonAddNewAddress);
        chooseAddress = findViewById(R.id.buttonChooseAddress);
        address = findViewById(R.id.tv_addressUser);
        editAddress = findViewById(R.id.btn_editAddress);
        nextStep = findViewById(R.id.buttonDeliverToThisAddress);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddress.this, Payment.class);
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
