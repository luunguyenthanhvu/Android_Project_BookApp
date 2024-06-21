package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.BookImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookImageRepository extends JpaRepository<BookImages, Long> {

  @Query("SELECT bi.url FROM Book_Images bi WHERE bi.book.bookId = :bookId")
  List<String> getAllByBookImageByBookId(@Param("bookId") long bookId);
}
