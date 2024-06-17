package nlu.hmuaf.android_bookapp.user.cart_user.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.Adapter.RecycleViewBookChosenAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.Adapter.RecycleViewBookForMyCartAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.Bean.Books;
import nlu.hmuaf.android_bookapp.R;

public class ReviewYourOrder extends AppCompatActivity {
    private StepView stepView;
    private List<String> listStepView = new ArrayList<>();
    private Toolbar toolbar;

    private RecyclerView recycleListBookChosen;
    private List<Books> listBook = new ArrayList<>();

    private Button btn_continue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_your_order);
        stepView = findViewById(R.id.stepViewInReviewYourOrder);
        toolbar = findViewById(R.id.toolbar2);
        recycleListBookChosen = findViewById(R.id.listBookChosen);

        listBook = (ArrayList<Books>)getIntent().getSerializableExtra("listBookChoose");

        RecycleViewBookForMyCartAdapter adapter = new RecycleViewBookForMyCartAdapter(this, listBook);
        recycleListBookChosen.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleListBookChosen.setLayoutManager(linearLayoutManager);
        recycleListBookChosen.setAdapter(adapter);


        btn_continue =findViewById(R.id.button3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listStepView.add("Review your order");
        listStepView.add("Address");
        listStepView.add("Payment");
        listStepView.add("Summary");

        stepView.getState().animationType(StepView.ANIMATION_ALL).steps(listStepView).animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime)).commit();
        stepView.go(0, true);

        listBook =(ArrayList<Books>) getIntent().getSerializableExtra("listBookChoose");
        HashMap<Integer,Integer> quantityMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("selectedQuantities");
        RecycleViewBookChosenAdapter adapter2 = new RecycleViewBookChosenAdapter(this, listBook,quantityMap);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleListBookChosen.setLayoutManager(linearLayoutManager2);
        recycleListBookChosen.setAdapter(adapter2);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewYourOrder.this, DeliveryAddress.class);
                intent.putExtra("listBookChoose", (ArrayList<Books>)listBook);
                intent.putExtra("selectedQuantities", quantityMap);
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
