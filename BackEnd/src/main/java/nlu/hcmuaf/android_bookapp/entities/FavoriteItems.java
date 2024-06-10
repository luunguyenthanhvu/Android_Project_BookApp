package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "Favorite_Items")
public class FavoriteItems implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "favoriteId")
  private Favorites favorite;

  @Id
  @ManyToOne
  @JoinColumn(name = "bookId")
  private Books book;
}
