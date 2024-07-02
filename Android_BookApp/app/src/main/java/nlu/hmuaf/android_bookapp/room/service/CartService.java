package nlu.hmuaf.android_bookapp.room.service;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import nlu.hmuaf.android_bookapp.dto.request.CartItemRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.client.DatabaseClient;
import nlu.hmuaf.android_bookapp.room.database.AppDatabase;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartService {
    private CartItemDao cartItemDao;
    private ExecutorService executorService;
    private MutableLiveData<Integer> cartSizeLiveData = new MutableLiveData<>();

    public LiveData<Integer> getCartSizeLiveData() {
        return cartSizeLiveData;
    }

    public CartService(Context context) {
        AppDatabase db = DatabaseClient.getInstance(context).getAppDatabase();
        cartItemDao = db.cartItemDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<CartItems> getUserCart(Long userId) {
        return cartItemDao.getCartItemByUserId(userId);
    }

    public List<CartItems> getItemUserChosen(Long userId, List<Long> itemChoose) {
        List<CartItems> userCartItems = cartItemDao.getCartItemByUserId(userId);
        List<CartItems> result = new ArrayList<>();
        for (CartItems cartItem : userCartItems) {
            if (itemChoose.contains(cartItem.getBookId())) {
                result.add(cartItem);
            }
        }
        return result;
    }

    public void updateQuantity(TokenResponseDTO tokenResponseDTO, long bookId, int quantity) {
        executorService.execute(() -> {
            try {
                CartItems item = cartItemDao.getById(bookId);
                if (item != null) {
                    if (quantity <= item.getAvailableQuantity()) {
                        cartItemDao.updateQuantity(bookId, quantity, tokenResponseDTO.getUserId());
                    } else {
                        cartItemDao.updateQuantity(bookId, item.getAvailableQuantity(), tokenResponseDTO.getUserId());
                    }
                    int cartSize = cartItemDao.getCartItemByUserId(tokenResponseDTO.getUserId()).size();
                    cartSizeLiveData.postValue(cartSize);
                } else {
                    // Xử lý trường hợp item không tồn tại
                    System.out.println("Cart item không tồn tại.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateProductCart(TokenResponseDTO tokenResponseDTO, final ListBookResponseDTO item, final int quantity) {
        executorService.execute(() -> {
            try {
                List<CartItems> listItem = cartItemDao.getCartItemByUserId(tokenResponseDTO.getUserId());
                boolean isProductInCart = isProductInCart(listItem, item.getBookId());
                if (isProductInCart) {
                    int cartQuantity = getCartItem(listItem, item.getBookId()).getQuantity();
                    if (cartQuantity + quantity > item.getQuantity()) {
                        cartQuantity = Math.toIntExact(item.getQuantity());
                    } else {
                        cartQuantity += quantity;
                    }
                    cartItemDao.updateQuantity(item.getBookId(), cartQuantity, tokenResponseDTO.getUserId());
                } else {
                    cartItemDao.insert(CartItems.builder()
                            .title(item.getTitle())
                            .quantity(quantity)
                            .bookId(item.getBookId())
                            .thumbnail(item.getThumbnail())
                            .availableQuantity(Math.toIntExact(item.getQuantity()))
                            .userId(tokenResponseDTO.getUserId())
                            .originalPrice(item.getOriginalPrice())
                            .discountedPrice(item.getDiscountedPrice())
                            .averageRating(item.getAverageRating())
                            .discount(item.getDiscount())
                            .build());
                }
                int cartSize = cartItemDao.getCartItemByUserId(tokenResponseDTO.getUserId()).size();
                cartSizeLiveData.postValue(cartSize);
//                if (!isProductInCart) {
//                    syncCartWithServer(tokenResponseDTO, "Default");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isProductInCart(List<CartItems> cartItems, long bookId) {
        return cartItems.stream().anyMatch(cartItem -> cartItem.getBookId() == bookId);
    }

    private CartItems getCartItem(List<CartItems> cartItems, long bookId) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }

    public void syncCartWithServer(TokenResponseDTO tokenResponseDTO, String action) {
        BookAppApi bookAppApi = BookAppService.getClient(tokenResponseDTO.getToken());
        Call<List<CartItems>> call = bookAppApi.getUserCart(tokenResponseDTO.getUserId());
        call.enqueue(new Callback<List<CartItems>>() {
            @Override
            public void onResponse(Call<List<CartItems>> call, Response<List<CartItems>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CartItems> serverCartItems = response.body();
                    syncCartDatabase(tokenResponseDTO, serverCartItems, action);
                } else {
                    System.out.println("Server returned error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CartItems>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    private void syncCartDatabase(TokenResponseDTO tokenResponseDTO, List<CartItems> serverCartItems, String action) {
        executorService.execute(() -> {
            try {
                List<CartItems> localCartItems = cartItemDao.getCartItemByUserId(tokenResponseDTO.getUserId());
                for (CartItems serverItem : serverCartItems) {
                    CartItems localItem = getCartItem(localCartItems, serverItem.getBookId());
                    if (localItem != null) {
                        int newQuantity;
                        switch (action) {
                            case "Login":
                            case "Default":
                                newQuantity = Math.min(serverItem.getQuantity(), localItem.getAvailableQuantity());
                                break;
                            case "Logout":
                                newQuantity = Math.min(localItem.getQuantity(), localItem.getAvailableQuantity());
                                break;
                            default:
                                newQuantity = 0;
                                break;
                        }
                        cartItemDao.updateQuantity(localItem.getBookId(), newQuantity, tokenResponseDTO.getUserId());
                    } else {
                        cartItemDao.insert(serverItem);
                    }
                }
                List<CartItems> localCartItemUpdated = cartItemDao.getCartItemByUserId(tokenResponseDTO.getUserId());
                int cartSize = localCartItemUpdated.size();
                cartSizeLiveData.postValue(cartSize);
                syncLocalCartToServer(localCartItemUpdated, tokenResponseDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void syncLocalCartToServer(TokenResponseDTO tokenResponseDTO) {
        executorService.execute(() -> {
            try {
                BookAppApi bookAppApi = BookAppService.getClient(tokenResponseDTO.getToken());
                List<CartItems> localCartItems = cartItemDao.getCartItemByUserId(tokenResponseDTO.getUserId());
                List<CartItemRequestDTO> requestDTO = localCartItems.stream()
                        .map(item -> new CartItemRequestDTO(item.getBookId(), item.getQuantity()))
                        .collect(Collectors.toList());
                System.out.println(requestDTO);
                Call<MessageResponseDTO> call = bookAppApi.updateUserCart(tokenResponseDTO.getUserId(), requestDTO);
                call.enqueue(new Callback<MessageResponseDTO>() {
                    @Override
                    public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                        if (response.isSuccessful()) {
                            System.out.println("CART SEVER STATUS");
                            System.out.println(response.body());
                            System.out.println("Cart synced successfully");
                        } else {
                            System.out.println("Failed to sync cart");
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {

                    }
                });
            } catch (Exception e) {

            }

        });
    }

    public void syncLocalCartToServer(List<CartItems> localCartItemUpdated, TokenResponseDTO
            tokenResponseDTO) {
        BookAppApi bookAppApi = BookAppService.getClient(tokenResponseDTO.getToken());
        List<CartItemRequestDTO> requestDTO = localCartItemUpdated.stream()
                .map(item -> new CartItemRequestDTO(item.getBookId(), item.getQuantity()))
                .collect(Collectors.toList());
        Call<MessageResponseDTO> call = bookAppApi.updateUserCart(tokenResponseDTO.getUserId(), requestDTO);
        call.enqueue(new Callback<MessageResponseDTO>() {
            @Override
            public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                if (response.isSuccessful()) {
                    System.out.println("CART SEVER STATUS");
                    System.out.println(response.body());
                    System.out.println("Cart synced successfully");
                } else {
                    System.out.println("Failed to sync cart");
                }
            }

            @Override
            public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {

            }
        });
    }

    public void deleteItem(CartItems items) {
        cartItemDao.delete(items);
    }

    public void deleteCartItemByBookId(long bookId) {
        cartItemDao.deleteCartItemByBookId(bookId);
    }
}
