package nlu.hcmuaf.android_bookapp.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsJson {
  @JsonProperty("bookFormat")
  private String bookFormat;
  @JsonProperty("size")
  private String size;
  @JsonProperty("numPage")
  private String numPage;
  @JsonProperty("author")
  private String author;
  @JsonProperty("PublishCompany")
  private PublishCompanyJson publishCompany;
}
