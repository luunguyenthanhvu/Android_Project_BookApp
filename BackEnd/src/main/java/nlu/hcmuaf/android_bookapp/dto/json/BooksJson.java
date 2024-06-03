package nlu.hcmuaf.android_bookapp.dto.json;

import jakarta.persistence.ElementCollection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksJson {

  private String code;
  private String title;
  private String price;
  private String thumbnail;
  private String description;
  @ElementCollection
  private List<String> bookImages;
  private BookDetailsJson bookDetails;
}
