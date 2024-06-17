package nlu.hmuaf.android_bookapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.room.service.CartService;

public class MyUtils {
    public static String convertToVND(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        Currency currency = Currency.getInstance(new Locale("vi", "VN"));
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        numberFormat.setCurrency(currency);
        return numberFormat.format(price);
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
        System.out.println(json);
        return gson.fromJson(json, TokenResponseDTO.class);
    }

    public static void updateCartQuantity(int quantityBookInCart, FrameLayout cartActionInclude) {
        TextView quantityTextView = cartActionInclude.findViewById(R.id.cart_badge_text_view);
        quantityTextView.setText(String.valueOf(quantityBookInCart));
        quantityTextView.setVisibility(quantityBookInCart == 0 ? View.GONE : View.VISIBLE);
    }
}
