package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, Long> {
  @Query("SELECT R FROM Ratings R")
  List<Ratings> getAllBy();
}
