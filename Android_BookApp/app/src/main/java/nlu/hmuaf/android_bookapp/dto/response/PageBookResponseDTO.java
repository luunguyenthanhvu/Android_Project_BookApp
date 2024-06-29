package nlu.hmuaf.android_bookapp.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageBookResponseDTO {

  private List<ListBookResponseDTO> bookResponseDTOList;
  private int totalPages;
}
