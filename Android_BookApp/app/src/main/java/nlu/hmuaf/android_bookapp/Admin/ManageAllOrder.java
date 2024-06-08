package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import nlu.hmuaf.android_bookapp.R;


public class ManageAllOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_management);


        TextView allOrder = findViewById(R.id.all_order);
        allOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang Activity NotificationActivity
                Intent intent = new Intent(ManageAllOrder.this, ManageAllOrder.class);
                startActivity(intent);
            }
        });

    }
}
