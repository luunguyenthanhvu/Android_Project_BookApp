package nlu.hcmuaf.android_bookapp.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListOrderResponseDTO {

  private long orderId;
  private List<CartItemResponseDTO> bookList;
  private double totalPrice;
  private String status;
  private String paymentMethod;
}
