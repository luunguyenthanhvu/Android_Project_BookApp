package nlu.hcmuaf.android_bookapp.service.templates;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.request.CartItemRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.CartItemResponseDTO;

public interface ICartService {

  List<CartItemResponseDTO> getUserCart(long userId);

  void syncCart(long userId, List<CartItemRequestDTO> requestDTO);

  void  deleteCartItem(long userId, long bookId);
}
