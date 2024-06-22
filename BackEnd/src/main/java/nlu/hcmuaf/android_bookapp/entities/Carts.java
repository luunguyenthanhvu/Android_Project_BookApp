package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cart")
public class Carts implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cartId")
  private long cartId;

  @Column(name = "userId", insertable = false, updatable = false)
  private long userId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  private Users user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  private Set<CartItems> cartItems;

  @Override
  public int hashCode() {
    return Objects.hash(cartId, user);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Carts cart = (Carts) o;
    return Objects.equals(cartId, cart.cartId) &&
        Objects.equals(user, cart.user);
  }
}
