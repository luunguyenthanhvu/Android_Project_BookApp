package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.Books;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Books, Long> {

  @Query("SELECT B FROM Books B")
  List<Books> getAllBy();
}
