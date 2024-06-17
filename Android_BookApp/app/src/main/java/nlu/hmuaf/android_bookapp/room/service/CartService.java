package nlu.hmuaf.android_bookapp.room.service;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.room.client.DatabaseClient;
import nlu.hmuaf.android_bookapp.room.database.AppDatabase;
import nlu.hmuaf.android_bookapp.room.entity.CartItem;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class CartService {
    private CartItemDao cartItemDao;
    private ExecutorService executorService;
    public int cartSize = 0;

    public CartService(Context context) {
        AppDatabase db = DatabaseClient.getInstance(context).getAppDatabase();
        cartItemDao = db.cartItemDao();
        executorService = Executors.newSingleThreadExecutor();
        getCartQuantity(MyUtils.getTokenResponse(context).getUsername());
    }

    private void getCartQuantity(String username) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (username != null) {
                        cartSize = cartItemDao.getCartItemByUsername(username).size();
                    } else {
                        cartSize = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateProductCart(final String username, final ListBookResponseDTO item, final int quantity) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CartItem> listItem = cartItemDao.getCartItemByUsername(username);
                    if (isProductInCart(listItem, item.getBookId())) {
                        cartItemDao.updateQuantity(item.getBookId(), quantity, username);
                        listItem.forEach(s -> System.out.println(s));
                    } else {
                        cartItemDao.insert(CartItem.builder()
                                .title(item.getTitle())
                                .quantity(quantity)
                                .bookId(item.getBookId())
                                .thumbnail(item.getThumbnail())
                                .availableQuantity(item.getQuantity())
                                .username(username)
                                .build());
                    }
                    cartSize = cartItemDao.getCartItemByUsername(username).size();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isProductInCart(List<CartItem> cartItems, long bookId) {
        if (cartItems.isEmpty()) return false;
        return cartItems.stream().anyMatch(cartItem -> cartItem.getBookId() == bookId);
    }

    public int getQuantityCart() {
        return cartSize;
    }

}
