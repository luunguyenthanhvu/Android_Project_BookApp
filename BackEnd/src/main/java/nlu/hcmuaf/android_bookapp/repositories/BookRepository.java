package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Books, Long>,
    JpaSpecificationExecutor<Books> {

  @Query("SELECT B FROM Books B")
  List<Books> getAllBy();

  @Query("SELECT B FROM Books B WHERE B.bookId = :bookId")
  Optional<Books> getBooksByBookId(@Param("bookId") long bookId);

  @Query(
      "SELECT new nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO(b.bookId, b.thumbnail, b.title, bd.author, AVG(br.rating.star), b.price, SUM(sd.quantity), dc.percent) "
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
      "SELECT new nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO(b.bookId, b.thumbnail, b.title, bd.author, AVG(br.rating.star), b.price, SUM(sd.quantity), dc.percent)"
          +
          "FROM Books b " +
          "LEFT JOIN b.bookDetails bd " +
          "LEFT JOIN b.bookRatings br " +
          "LEFT JOIN b.shipmentDetails sd " +
          "LEFT JOIN sd.shipment s " +
          "LEFT JOIN b.discounts dc " +
          "WHERE dc.percent > 0 " +
          "GROUP BY b.bookId, b.thumbnail, b.title, bd.author, b.price, sd.quantity,  dc.percent "
          +
          "ORDER BY s.dateAdded DESC"
  )
  Page<ListBookResponseDTO> getDiscountBookList(Pageable pageable);

  @Query("SELECT b FROM Books b WHERE b.title LIKE %:title%")
  List<Books> getBooksByTitle(@Param("title") String title);

  @Query(
      "SELECT new nlu.hcmuaf.android_bookapp.dto.response.BookDetailResponseDTO(b.bookId,  b.title, bd.author,SUM(sd.quantity) , AVG(br.rating.star) , b.price,dc.percent, b.description, pl.companyName)"
          +
          "FROM Books b " +
          "LEFT JOIN b.bookDetails bd " +
          "LEFT JOIN b.bookRatings br " +
          "LEFT JOIN b.shipmentDetails sd " +
          "LEFT JOIN sd.shipment s " +
          "LEFT JOIN b.discounts dc " +
          "LEFT JOIN bd.publishCompany pl " +
          "WHERE b.bookId = :bookId " +
          "GROUP BY b.bookId,  b.title, bd.author , b.price,dc.percent, b.description, pl.companyName"
  )
  Optional<BookDetailResponseDTO> getBooksDetailsByBookId(@Param("bookId") long bookId);
}
