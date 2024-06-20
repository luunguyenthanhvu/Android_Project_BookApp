package nlu.hmuaf.android_bookapp.sync;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModel;

import java.util.List;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.service.CartService;

public class HomeViewModel extends ViewModel {
    private final CartService cartService;
    private final long userId;
    private boolean cartChanged = false;
    private final Handler handler;

    public HomeViewModel(CartService cartService, long userId) {
        this.cartService = cartService;
        this.userId = userId;
        handler = new Handler(Looper.getMainLooper());
    }

    private final Runnable syncRunnable = new Runnable() {
        @Override
        public void run() {
            if (cartChanged) {
//                syncCartToServer();
                cartChanged = false;
            }
        }
    };

    public void onCartChanged() {
        cartChanged = true;
        handler.removeCallbacks(syncRunnable);
        handler.postDelayed(syncRunnable, 60 * 1000); // Trì hoãn 1 phút
    }

//    public void syncCartToServer() {
//        List<CartItems> cartItems = cartService.getUserCart(userId);
//        cartService.updateCartOnServer(username, cartItems, new CartService.CartUpdateCallback() {
//            @Override
//            public void onSuccess() {
//                // Xử lý khi đồng bộ thành công (nếu cần thiết)
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                // Xử lý khi đồng bộ thất bại (nếu cần thiết)
//            }
//        });
//    }
}
