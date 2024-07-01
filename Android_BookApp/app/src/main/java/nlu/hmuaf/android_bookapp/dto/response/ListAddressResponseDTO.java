package nlu.hmuaf.android_bookapp.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListAddressResponseDTO implements Serializable {

  private long addressId;
  private String addressDetails;
  private boolean mainAddress;
}
