package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.dto.response.CartItemResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {

  @Query("SELECT c FROM Cart c")
  List<Carts> getAllBy();

  @Query(
      "SELECT new nlu.hcmuaf.android_bookapp.dto.response.CartItemResponseDTO(c.userId, ci.book.bookId, ci.book.title, ci.book.thumbnail, ci.quantity, SUM(sd.quantity), AVG(br.rating.star), b.price, dc.percent)"
          +
          "FROM Cart c " +
          "LEFT JOIN c.cartItems ci " +
          "LEFT JOIN ci.book b " +
          "LEFT JOIN b.bookDetails bd " +
          "LEFT JOIN b.bookRatings br " +
          "LEFT JOIN b.shipmentDetails sd " +
          "LEFT JOIN sd.shipment s " +
          "LEFT JOIN b.discounts dc " +
          "WHERE c.userId = :userId " +
          "GROUP BY c.userId, ci.book.bookId, ci.book.title, ci.book.thumbnail, ci.quantity,sd.quantity, b.price, dc.percent"
  )
  List<CartItemResponseDTO> getUserCartItem(@Param("userId") long userId);

  @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci WHERE c.userId = :userId")
  Optional<Carts> getAllByUserId(@Param("userId") long userId);
}
