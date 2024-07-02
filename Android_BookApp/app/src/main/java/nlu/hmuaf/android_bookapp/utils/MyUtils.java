package nlu.hmuaf.android_bookapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.security.auth.callback.Callback;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.login.ForgotPassword;
import nlu.hmuaf.android_bookapp.user.login.Login;

public class MyUtils {
    public static String convertToVND(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        Currency currency = Currency.getInstance(new Locale("vi", "VN"));
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        numberFormat.setCurrency(currency);
        return numberFormat.format(price);
    }

    public static void deleteTokenResponse(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BookAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("TokenResponseDTO");
        editor.apply();
    }

    public static void saveTokenResponse(Context context, TokenResponseDTO responseDTO) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BookAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(responseDTO);
        editor.putString("TokenResponseDTO", json);
        editor.apply();
    }

    public static TokenResponseDTO getTokenResponse(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BookAppPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("TokenResponseDTO", null);
        return gson.fromJson(json, TokenResponseDTO.class);
    }

    // Example method to store API key in SharedPreferences
    public static void saveApiKey(Context context, String apiKey) {
        SharedPreferences preferences = context.getSharedPreferences("API_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("google_maps_api_key", apiKey);
        editor.apply();
    }

    public static String getApiKey(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("API_PREFS", Context.MODE_PRIVATE);
        String apiKey = preferences.getString("google_maps_api_key", null); // Return null if not found
        return apiKey;
    }

    public static boolean isTokenExpiration(Context context) {
        try {
            JWT jwt = new JWT(MyUtils.getTokenResponse(context).getToken());
            Date dateExpire = jwt.getExpiresAt();
            System.out.println(dateExpire);
            if (dateExpire.before(new Date())) {
                // Token đã hết hạn
                showTokenExpireDialog(context);
                return false;
            } else {
                // Token chưa hết hạn
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserLoggedIn(Activity activity) {
        if (getTokenResponse(activity) != null) {
            return isTokenExpiration(activity);
        }
        showLoginDialog(activity);
        return false;
    }

    private static void showTokenExpireDialog(Context context) {
        MyUtils.deleteTokenResponse(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Vui Lòng Đăng Nhập Lại!");
        builder.setMessage("Phiên đăng nhập của bạn đã hết hạn vui lòng đăng nhập lại.");
        builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý logic khi người dùng nhấn nút Hủy
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void showLoginDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Vui Lòng đăng nhập");
        builder.setMessage("Bạn cần đăng nhập để sử dụng chức năng này");
        builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý logic khi người dùng nhấn nút Hủy
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
