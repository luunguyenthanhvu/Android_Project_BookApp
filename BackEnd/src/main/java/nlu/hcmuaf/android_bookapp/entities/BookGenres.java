package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Book_Genres")
public class BookGenres implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "bookId")
  private Books book;

  @Id
  @ManyToOne
  @JoinColumn(name = "genreId")
  private Genres genre;

}
