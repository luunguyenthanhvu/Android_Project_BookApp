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
public class CartItemResponseDTO implements Serializable {
  private long userId;
  private long bookId;
  private String title;
  private String thumbnail;
  /**
   * Cart quantity
   */
  private int quantity;
  /**
   * Product available quantity
   */
  private int availableQuantity;
  private Double averageRating;
  private double originalPrice;
  private double discountedPrice;
  private Double discount;

  public CartItemResponseDTO(long userId, long bookId, String title, String thumbnail, int quantity,
      int availableQuantity, Double averageRating, double originalPrice,
      Double discount) {
    this.userId = userId;
    this.bookId = bookId;
    this.title = title;
    this.thumbnail = thumbnail;
    this.quantity = quantity;
    this.availableQuantity = availableQuantity;
    this.averageRating = averageRating;
    this.originalPrice = originalPrice;
    this.discountedPrice = (discount != null) ? originalPrice * (1 - discount) : 0.0;
    this.discount = (discount != null) ? discount : 0.0;
  }
}
