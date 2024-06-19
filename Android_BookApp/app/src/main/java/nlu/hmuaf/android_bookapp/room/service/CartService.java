package nlu.hmuaf.android_bookapp.room.service;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.room.client.DatabaseClient;
import nlu.hmuaf.android_bookapp.room.database.AppDatabase;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;

public class CartService {
    private CartItemDao cartItemDao;
    private ExecutorService executorService;
    public List<CartItems> cartItems = new ArrayList<>();
    private MutableLiveData<Integer> cartSizeLiveData = new MutableLiveData<>();

    // Hàm này để trả về LiveData để activity có thể lắng nghe sự thay đổi trong giỏ hàng
    public LiveData<Integer> getCartSizeLiveData() {
        return cartSizeLiveData;
    }

    public CartService(Context context) {
        AppDatabase db = DatabaseClient.getInstance(context).getAppDatabase();
        cartItemDao = db.cartItemDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void checkUserCart(String username) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cartItems = cartItemDao.getCartItemByUsername(username);
                    cartItems = cartItems.isEmpty() ? new ArrayList<>() : cartItems;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<CartItems> getUserCart(String username) {
        return cartItemDao.getCartItemByUsername(username);
    }

    public void updateQuantity(String username, long bookId, int quantity) {
        executorService.execute(() -> {
            try {
                CartItems item = cartItemDao.getById(bookId);
                if (quantity <= item.getAvailableQuantity()) {
                    cartItemDao.updateQuantity(bookId, quantity, username);
                } else {
                    cartItemDao.updateQuantity(bookId, item.getAvailableQuantity(), username);
                }
                int cartSize = cartItemDao.getCartItemByUsername(username).size();
                cartSizeLiveData.postValue(cartSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateProductCart(final String username, final ListBookResponseDTO item, final int quantity) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CartItems> listItem = cartItemDao.getCartItemByUsername(username);
                    if (isProductInCart(listItem, item.getBookId())) {
                        int cartQuantity = item.getQuantity();
                        if (cartQuantity + quantity <= item.getQuantity()) {
                            cartQuantity += quantity;
                        } else {
                            cartQuantity = item.getQuantity();
                        }
                        cartItemDao.updateQuantity(item.getBookId(), cartQuantity, username);
                    } else {
                        cartItemDao.insert(CartItems.builder()
                                .title(item.getTitle())
                                .quantity(quantity)
                                .bookId(item.getBookId())
                                .thumbnail(item.getThumbnail())
                                .availableQuantity(item.getQuantity())
                                .username(username)
                                .originalPrice(item.getOriginalPrice())
                                .discountedPrice(item.getDiscountedPrice())
                                .averageRating(item.getAverageRating())
                                .discount(item.getDiscount())
                                .build());
                    }
                    int cartSize = cartItemDao.getCartItemByUsername(username).size();
                    cartSizeLiveData.postValue(cartSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isProductInCart(List<CartItems> cartItems, long bookId) {
        if (cartItems.isEmpty()) return false;
        return cartItems.stream().anyMatch(cartItem -> cartItem.getBookId() == bookId);
    }

}
