package nlu.hcmuaf.android_bookapp.entities.embeddale;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartItemsId implements Serializable {

  private Long cart;
  private Long book;

  // Constructors, getters, and setters (if needed)
  public CartItemsId() {
  }

  public CartItemsId(Long cart, Long book) {
    this.cart = cart;
    this.book = book;
  }

  // Equals and hashCode methods (based on cart and book fields)

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CartItemsId that = (CartItemsId) o;

    if (!cart.equals(that.cart)) {
      return false;
    }
    return book.equals(that.book);
  }

  @Override
  public int hashCode() {
    int result = cart.hashCode();
    result = 31 * result + book.hashCode();
    return result;
  }
}
