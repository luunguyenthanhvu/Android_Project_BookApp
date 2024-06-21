package nlu.hcmuaf.android_bookapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListBookResponseDTO {

  private long bookId;
  private String thumbnail;
  private String title;
  private String author;
  private Double averageRating;
  private double originalPrice;
  private double discountedPrice;
  private Long quantity;
  private Double discount;

  public ListBookResponseDTO(long bookId, String thumbnail, String title, String author,
      Double averageRating, double originalPrice, Long quantity, Double discount) {
    this.bookId = bookId;
    this.thumbnail = thumbnail;
    this.title = title;
    this.author = author;
    this.averageRating = averageRating;
    this.discountedPrice = (discount != null) ? originalPrice * (1 - discount) : 0.0;
    this.originalPrice = originalPrice;
    this.quantity = quantity;
    this.discount = (discount != null) ? discount : 0.0;
  }
}
