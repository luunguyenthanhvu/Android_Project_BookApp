package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Ratings")
public class Ratings implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ratingId")
  private long ratingId;

  @Column(name = "comment")
  private String comment;

  @Column(name = "star")
  private float star;

  @OneToMany(mappedBy = "rating", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<BookRating> bookRatings;

  @ManyToOne
  @JoinColumn(name = "userId")
  private UserDetails userDetails;

  @Override
  public int hashCode() {
    return Objects.hash(ratingId, comment, star);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ratings ratings = (Ratings) o;
    return ratingId == ratings.ratingId && star == ratings.star;
  }
}
