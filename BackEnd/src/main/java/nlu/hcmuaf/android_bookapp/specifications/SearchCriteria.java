package nlu.hcmuaf.android_bookapp.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

  private String key;
  private String operation;
  private Object value;

  public SearchCriteria(String key) {
    this.key = key;
  }
}
