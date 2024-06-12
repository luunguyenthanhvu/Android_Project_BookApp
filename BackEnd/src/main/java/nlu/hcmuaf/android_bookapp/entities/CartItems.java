package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "Cart_Items")
public class CartItems implements Serializable {
  @Id
  @ManyToOne
  @JoinColumn(name = "cartId")
  private Carts cart;

  @Id
  @ManyToOne
  @JoinColumn(name = "bookId")
  private Books book;

  @Column(name = "quantity")
  private int quantity;
}
