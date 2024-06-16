package nlu.hmuaf.android_bookapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import nlu.hmuaf.android_bookapp.R;

public class AdminHome extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout orderManagementSubmenu, productManagementSubmenu, userManagementSubmenu;
    private TextView navSalesManagement , listOrderSubMenu;
    private ImageView navOrderManagement,navProductManagement,navUserManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        // Khai báo
        drawerLayout = findViewById(R.id.drawer_layout);
        productManagementSubmenu = findViewById(R.id.product_management_submenu);
        userManagementSubmenu = findViewById(R.id.user_management_submenu);

        // Quản lý đơn hàng chứa: Danh sách đơn hàng, Thêm đơn hàng và Danh sách tồn kho
        orderManagementSubmenu = findViewById(R.id.order_management_submenu);
        // nút hiển thị các nav
        navOrderManagement = findViewById(R.id.nav_order_management);
        navOrderManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSubMenu(orderManagementSubmenu);
            }
        });

        // Chọn Danh sách đơn hàng
        listOrderSubMenu = findViewById(R.id.list_order_submenu);
        listOrderSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(AdminHome.this, ManageOrder.class);
                startActivity(intent);
            }
        });


        // Chọn quản lý sản phẩm -> Danh sách sản phẩm và Thêm đơn hàng
        navProductManagement = findViewById(R.id.nav_product_management);
        navProductManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSubMenu(productManagementSubmenu);
            }
        });



        // Chọn doanh thu
        navSalesManagement = findViewById(R.id.nav_sales_mangagement);
        navSalesManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(AdminHome.this, HomeActivity.class);
//                startActivity(intent);
            }
        });

        // Chọn quản lý người dùng
        navUserManagement = findViewById(R.id.nav_user_management);
        navUserManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSubMenu(userManagementSubmenu);
            }
        });

    }

    // hàm hiện ra thanh menu
    private void toggleSubMenu(LinearLayout submenu) {
        if (submenu.getVisibility() == View.GONE) {
            submenu.setVisibility(View.VISIBLE);
        } else {
            submenu.setVisibility(View.GONE);
        }
    }
}
