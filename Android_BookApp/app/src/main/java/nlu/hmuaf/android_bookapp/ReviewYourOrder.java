package nlu.hmuaf.android_bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class ReviewYourOrder extends AppCompatActivity {
    private StepView stepView;
    private List<String> listStepView = new ArrayList<>();
    private Toolbar toolbar;
    private ImageView imageBook;
    private ImageView iconLocation;
    private TextView textAuthorName;
    private TextView textBookName;
    private TextView textBookRate;
    private TextView title;
    private  TextView address;
    private Button change;
    private Button btn_continue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_your_order);
        stepView = findViewById(R.id.stepViewInReviewYourOrder);
        toolbar = findViewById(R.id.toolbar2);

        iconLocation =findViewById(R.id.imageView2);

        title =findViewById(R.id.textView9);
        address = findViewById(R.id.textView8);
        change = findViewById(R.id.button5);
        btn_continue =findViewById(R.id.button3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listStepView.add("Cart");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");

        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
        stepView.go(0, true);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewYourOrder.this, DeliveryAddress.class);
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
