package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import nlu.hmuaf.android_bookapp.R;

public class AdminHome extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout orderManagementSubmenu;
    private TextView navOrderManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        orderManagementSubmenu = findViewById(R.id.order_management_submenu);
        navOrderManagement = findViewById(R.id.nav_order_management);

        navOrderManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSubMenu(orderManagementSubmenu);
            }
        });
    }

    private void toggleSubMenu(LinearLayout submenu) {
        if (submenu.getVisibility() == View.GONE) {
            submenu.setVisibility(View.VISIBLE);
        } else {
            submenu.setVisibility(View.GONE);
        }
    }
}
