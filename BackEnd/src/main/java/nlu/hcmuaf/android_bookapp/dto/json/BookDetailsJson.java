package nlu.hcmuaf.android_bookapp.dto.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsJson {

  private String bookFormat;
  private String size;
  private String numPage;
  private String author;
  private String companyName;
}
