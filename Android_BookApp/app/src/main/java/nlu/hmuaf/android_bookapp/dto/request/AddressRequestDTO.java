package nlu.hmuaf.android_bookapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {

  private long addressId;
  private String addressDetails;
  private boolean mainAddress;
}
