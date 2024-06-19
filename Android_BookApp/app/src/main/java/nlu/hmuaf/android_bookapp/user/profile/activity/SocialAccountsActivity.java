package nlu.hmuaf.android_bookapp.user.profile.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import nlu.hmuaf.android_bookapp.R;

public class SocialAccountsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_social_accounts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupAccountLink(R.id.facebookLink, "com.facebook.katana");
        setupAccountLink(R.id.googleLink, "com.google.android.youtube");
        setupAccountLink(R.id.instagramLink, "com.instagram.android");
        setupAccountLink(R.id.tiktokLink, "com.zhiliaoapp.musically");
        setupAccountLink(R.id.youtubeLink, "com.google.android.youtube");
    }

    private void setupAccountLink(int textViewId, String packageName) {
        TextView linkTextView = findViewById(textViewId);
        linkTextView.setTag("LINK"); // Đặt trạng thái ban đầu là "LINK"
        linkTextView.setOnClickListener(v -> toggleLinkStatus(linkTextView, packageName));
    }

    private void toggleLinkStatus(TextView linkTextView, String packageName) {
        String currentStatus = (String) linkTextView.getTag();
        if ("LINK".equals(currentStatus)) {
            openApp(packageName);
            linkTextView.setTag("UNLINK");
            linkTextView.setText("UNLINK");
        } else {
            linkTextView.setTag("LINK");
            linkTextView.setText("LINK");
        }
    }

    private void openApp(String packageName) {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            // App is not installed, open in browser
            String url = getAppUrl(packageName);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(webIntent);
        }
    }

    private String getAppUrl(String packageName) {
        switch (packageName) {
            case "com.facebook.katana":
                return "https://www.facebook.com";
            case "com.instagram.android":
                return "https://www.instagram.com";
            case "com.zhiliaoapp.musically":
                return "https://www.tiktok.com";
            case "com.google.android.youtube":
                return "https://www.youtube.com";
            default:
                return "https://www.google.com";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
