package nlu.hcmuaf.android_bookapp.repositories;

import nlu.hcmuaf.android_bookapp.entities.Books;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Books, Long> {

  public Books getBooksByAuthor(String authorName);
}
