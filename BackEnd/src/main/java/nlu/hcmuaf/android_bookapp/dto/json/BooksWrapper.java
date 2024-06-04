package nlu.hcmuaf.android_bookapp.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksWrapper {

  @JsonProperty("Books")
  private BooksJson books;
}
