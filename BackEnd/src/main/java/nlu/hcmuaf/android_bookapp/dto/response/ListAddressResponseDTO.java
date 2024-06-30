package nlu.hcmuaf.android_bookapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListAddressResponseDTO {

  private long addressId;
  private String addressDetails;
  private boolean mainAddress;
}
