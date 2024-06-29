package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.DarkModeUtil;
import nlu.hmuaf.android_bookapp.user.profile.classess.Notification;
import nlu.hmuaf.android_bookapp.user.profile.adapter.NotificationAdapter;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_notification);
        DarkModeUtil.applyDarkMode(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");

        // Thêm nút quay lại trên Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.notifications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu kiểm thử
        notificationList = new ArrayList<>();
        notificationList.add(new Notification("New Book Release", "Check out the new release of 'The Great Gatsby'.", "2023-05-01 10:00 AM"));
        notificationList.add(new Notification("Special Discount", "Get 20% off on all science fiction books.", "2023-05-02 02:00 PM"));
        notificationList.add(new Notification("Author Meet & Greet", "Join us for a meet and greet with J.K. Rowling.", "2023-05-03 09:00 AM"));
        // Thêm nhiều dữ liệu hơn nếu cần

        // Thiết lập adapter
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi người dùng nhấn vào nút quay lại trên Toolbar
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
