package nlu.hcmuaf.android_bookapp.repositories;

import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.CartItems;
import nlu.hcmuaf.android_bookapp.entities.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {

  void deleteCartItemsByBookAndCart(Carts carts, Books books);
}
