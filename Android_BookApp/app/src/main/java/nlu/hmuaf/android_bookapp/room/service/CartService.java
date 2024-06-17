package nlu.hmuaf.android_bookapp.room.service;

import java.util.List;

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.room.entity.CartItem;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;

public class CartService {
    private CartItemDao cartItemDao;

    public void updateProductCart(String username, ListBookResponseDTO item, int quantity) {
        List<CartItem> listItem = cartItemDao.getCartItemByUsername(username);
        if (!listItem.isEmpty()) {
            if (isProductInCart(listItem, item.getBookId())) {
                cartItemDao.updateQuantity(item.getBookId(), quantity, username);
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
        }
    }

    public boolean isProductInCart(List<CartItem> cartItems, long bookId) {
        return cartItems.stream().anyMatch(item -> item.getBookId() == bookId);
    }
}
