package nlu.hcmuaf.android_bookapp.repositories;

import jakarta.transaction.Transactional;
import nlu.hcmuaf.android_bookapp.entities.CartItems;
import nlu.hcmuaf.android_bookapp.entities.embeddale.CartItemsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, CartItemsId> {

  @Transactional
  @Modifying
  @Query("DELETE FROM Cart_Items c WHERE c.cart.cartId = :cartId AND c.book.bookId = :bookId")
  void deleteCartItemsByCartAndBook(@Param("cartId") long cartId, @Param("bookId") long bookId);
}
