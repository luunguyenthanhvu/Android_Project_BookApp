package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {

  @Query("SELECT B FROM Books B")
  List<Books> getAllBy();

  @Query(
      "SELECT new nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO(b.bookId, b.thumbnail, b.title, bd.author, AVG(br.rating.star), b.price, sd.quantity, dc.percent)"
          +
          "FROM Books b " +
          "LEFT JOIN b.bookDetails bd " +
          "LEFT JOIN b.bookRatings br " +
          "LEFT JOIN b.shipmentDetails sd " +
          "LEFT JOIN sd.shipment s " +
          "LEFT JOIN b.discounts dc " +
          "GROUP BY b.bookId, b.thumbnail, b.title, bd.author, b.price, sd.quantity, dc.percent " +
          "ORDER BY s.dateAdded DESC"
  )
  Page<ListBookResponseDTO> getNewBookList(Pageable pageable);

  @Query(
      "SELECT new nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO(b.bookId, b.thumbnail, b.title, bd.author, AVG(br.rating.star), b.price, sd.quantity, dc.percent)"
          +
          "FROM Books b " +
          "LEFT JOIN b.bookDetails bd " +
          "LEFT JOIN b.bookRatings br " +
          "LEFT JOIN b.shipmentDetails sd " +
          "LEFT JOIN sd.shipment s " +
          "LEFT JOIN b.discounts dc " +
          "WHERE dc.percent > 0 " +
          "GROUP BY b.bookId, b.thumbnail, b.title, bd.author, b.price, sd.quantity, dc.percent " +
          "ORDER BY s.dateAdded DESC"
  )
  Page<ListBookResponseDTO> getDiscountBookList(Pageable pageable);

  @Query("SELECT b FROM Books b WHERE b.title LIKE %:title%")
  List<Books> getBooksByTitle(@Param("title") String title);

}
