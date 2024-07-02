package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import nlu.hcmuaf.android_bookapp.dto.request.CartItemRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.CartItemResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.CartItems;
import nlu.hcmuaf.android_bookapp.entities.Carts;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.repositories.CartItemRepository;
import nlu.hcmuaf.android_bookapp.repositories.CartRepository;
import nlu.hcmuaf.android_bookapp.service.templates.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements ICartService {

  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private CartItemRepository cartItemRepository;

  @Override
  public List<CartItemResponseDTO> getUserCart(long userId) {
    System.out.println("Lấy cart");
    List<CartItemResponseDTO> result = new ArrayList<>();
    result = cartRepository.getUserCartItem(userId);
    System.out.println(result);
    return result.isEmpty() ? new ArrayList<>() : result;
  }

  @Override
  public void syncCart(long userId, List<CartItemRequestDTO> requestDTO) {
    try {
      Optional<Carts> cartData = cartRepository.getAllByUserId(userId);
      if (cartData.isPresent()) {
        requestDTO.forEach(i -> System.out.println(i));
        Carts carts = cartData.get();
        Set<CartItems> cartItems = carts.getCartItems();
        Map<Long, CartItems> mapCartItemsServer = cartItems.stream()
            .collect(Collectors.toMap(i -> i.getBook().getBookId(), i -> i));
        Set<CartItems> newCartItems = new HashSet<>();
        for (CartItemRequestDTO cartItemRequestDTO : requestDTO) {
          CartItems item = mapCartItemsServer.get(cartItemRequestDTO.getBookId());
          System.out.println(cartItemRequestDTO);
          if (item != null) {
            item.setQuantity(cartItemRequestDTO.getQuantity());
            newCartItems.add(item);
          } else {
            Books book = bookRepository.getReferenceById(cartItemRequestDTO.getBookId());
            if (book != null) {
              item = CartItems.builder().cart(carts).book(book)
                  .quantity(cartItemRequestDTO.getQuantity()).build();
              newCartItems.add(item);
            }
          }
        }
        carts.setCartItems(newCartItems);
        cartRepository.save(carts);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteCartItem(long userId, long bookId) {
    try {
      Optional<Carts> cartData = cartRepository.getAllByUserId(userId);
      if (cartData.isPresent()) {
        Carts carts = cartData.get();
        cartItemRepository.deleteCartItemsByCartAndBook(carts.getCartId(), bookId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
