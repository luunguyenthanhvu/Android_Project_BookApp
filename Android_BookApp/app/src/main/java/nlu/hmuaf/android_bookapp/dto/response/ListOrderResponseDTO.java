package nlu.hmuaf.android_bookapp.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListOrderResponseDTO implements Serializable {
    private long orderId;
    private List<CartItemResponseDTO> bookList;
    private double totalPrice;
    private String status;
    private String paymentMethod;
}
