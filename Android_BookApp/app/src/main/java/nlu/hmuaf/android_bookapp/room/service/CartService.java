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

    public void checkUserCart(Long userId) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cartItems = cartItemDao.getCartItemByUserId(userId);
                    cartItems = cartItems.isEmpty() ? new ArrayList<>() : cartItems;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<CartItems> getUserCart(Long userId) {
        return cartItemDao.getCartItemByUserId(userId);
    }

    public void updateQuantity(Long userId, long bookId, int quantity) {
        executorService.execute(() -> {
            try {
                CartItems item = cartItemDao.getById(bookId);
                if (quantity <= item.getAvailableQuantity()) {
                    cartItemDao.updateQuantity(bookId, quantity, userId);
                } else {
                    cartItemDao.updateQuantity(bookId, item.getAvailableQuantity(), userId);
                }
                int cartSize = cartItemDao.getCartItemByUserId(userId).size();
                cartSizeLiveData.postValue(cartSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateProductCart(final Long userId, final ListBookResponseDTO item, final int quantity) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CartItems> listItem = cartItemDao.getCartItemByUserId(userId);
                    if (isProductInCart(listItem, item.getBookId())) {
                        int cartQuantity = getCartItem(listItem, item.getBookId()).getQuantity();
                        // Kiểm tra nếu tổng cartQuantity hiện tại và quantity mới thêm vào lớn hơn item.getQuantity()
                        if (cartQuantity + quantity > item.getQuantity()) {
                            // Nếu lớn hơn, chỉ cập nhật thành item.getQuantity()
                            cartQuantity = item.getQuantity();
                        } else {
                            // Nếu nhỏ hơn hoặc bằng, cộng thêm quantity mới vào cartQuantity
                            cartQuantity += quantity;
                        }
                        cartItemDao.updateQuantity(item.getBookId(), cartQuantity, userId);
                    } else {
                        // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
                        cartItemDao.insert(CartItems.builder()
                                .title(item.getTitle())
                                .quantity(quantity)
                                .bookId(item.getBookId())
                                .thumbnail(item.getThumbnail())
                                .availableQuantity(item.getQuantity())
                                .userId(userId)
                                .originalPrice(item.getOriginalPrice())
                                .discountedPrice(item.getDiscountedPrice())
                                .averageRating(item.getAverageRating())
                                .discount(item.getDiscount())
                                .build());
                    }
                    // Sau khi cập nhật hoặc thêm mới, cập nhật lại LiveData
                    int cartSize = cartItemDao.getCartItemByUserId(userId).size();
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

    private CartItems getCartItem(List<CartItems> cartItems, long bookId) {
        for (CartItems cartItem : cartItems) {
            if (cartItem.getBookId() == bookId) {
                return cartItem;
            }
        }
        return null;
    }

}
