package nlu.hcmuaf.android_bookapp.controller;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.request.CartItemRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.CartItemResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.service.templates.ICartService;
import nlu.hcmuaf.android_bookapp.service.templates.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired
  private IUserService userService;
  @Autowired
  private ICartService cartService;

  @GetMapping("/cart/{userId}")
  public ResponseEntity<List<CartItemResponseDTO>> getUserCart(@PathVariable long userId) {
    List<CartItemResponseDTO> cartItems = cartService.getUserCart(userId);
    return ResponseEntity.ok(cartItems);
  }

  @PostMapping("/cart/update/{userId}")
  public ResponseEntity<MessageResponseDTO> syncCart(@PathVariable long userId,
      @RequestBody List<CartItemRequestDTO> requestDTO) {
    System.out.println("update nè");
    System.out.println(requestDTO);
    cartService.syncCart(userId, requestDTO);
    return ResponseEntity.ok(MessageResponseDTO.builder().message("Success").build());
  }

  @PostMapping("/cart/delete/{userId}/{bookId}")
  public ResponseEntity<MessageResponseDTO> deleteCartItem(@PathVariable long userId,
      @PathVariable long bookId) {
    cartService.deleteCartItem(userId, bookId);
    return ResponseEntity.ok(MessageResponseDTO.builder().message("Success").build());
  }

  @GetMapping("/address/{userId}")
  public ResponseEntity<List<ListAddressResponseDTO>> getUserAddress(@PathVariable long userId) {
    List<ListAddressResponseDTO> addressList = userService.getListAddress(userId);
    if (addressList.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(addressList);
  }
}
