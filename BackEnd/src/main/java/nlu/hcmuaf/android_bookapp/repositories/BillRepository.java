package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bills, Long> {

  @Query("SELECT B FROM Bills B JOIN FETCH B.billDetails BD where B.user.userId = :userId")
  List<Bills> findAllByUserId(@Param("userId") long userId);
}
