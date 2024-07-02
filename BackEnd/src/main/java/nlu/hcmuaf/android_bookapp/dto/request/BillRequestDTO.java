package nlu.hcmuaf.android_bookapp.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequestDTO {

  List<CartItemRequestDTO> cartItemRequestDTO;
  long addressId;
  String paymentMethod;

}

