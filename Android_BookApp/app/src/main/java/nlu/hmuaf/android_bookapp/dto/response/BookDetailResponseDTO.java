package nlu.hmuaf.android_bookapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDetailResponseDTO {

  private long bookId;
  private String[] img;
  private String title;
  private String author;
  private int availableQuantity;
  private Double averageRating;
  private double originalPrice;
  private double discountedPrice;
  private Double discount;
  private String description;
  private String details;
  private String publicCompany;

  public BookDetailResponseDTO(long bookId, String title, String author,
      Long availableQuantity, Double averageRating, double originalPrice,
      Double discount, String description,String publicCompany) {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.availableQuantity = Math.toIntExact(availableQuantity);
    this.averageRating = averageRating;
    this.originalPrice = originalPrice;
    this.discountedPrice = (discount != null) ? originalPrice * (1 - discount) : 0.0;
    this.discount = (discount != null) ? discount : 0.0;
    this.description = description;
    this.publicCompany = publicCompany;
  }
}
