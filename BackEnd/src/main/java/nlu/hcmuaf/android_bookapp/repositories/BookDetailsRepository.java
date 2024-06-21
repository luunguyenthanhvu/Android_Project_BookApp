package nlu.hcmuaf.android_bookapp.repositories;

import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

  @Query("SELECT bd FROM Book_Details bd where bd.bookId = :bookId")
  Optional<BookDetails> getAllByBookId(@Param("bookId") long bookId);
}
