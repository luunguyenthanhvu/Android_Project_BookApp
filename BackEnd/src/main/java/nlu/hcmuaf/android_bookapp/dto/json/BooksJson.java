package nlu.hcmuaf.android_bookapp.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksJson {
  @JsonProperty("code")
  private String code;
  @JsonProperty("title")
  private String title;
  @JsonProperty("price")
  private String price;
  @JsonProperty("thumbnail")
  private String thumbnail;
  @JsonProperty("description")
  private String description;
  @JsonProperty("bookImages")
  private String[] bookImages;
  @JsonProperty("BookDetails")
  private BookDetailsJson bookDetails;
}
