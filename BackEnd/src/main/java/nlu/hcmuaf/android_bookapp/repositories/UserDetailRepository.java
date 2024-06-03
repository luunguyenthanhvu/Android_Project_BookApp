package nlu.hcmuaf.android_bookapp.repositories;

import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.UserDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDetailRepository extends CrudRepository<UserDetails, Long> {

  @Query("SELECT u FROM User_Details u WHERE u.email = :email")
  Optional<UserDetails> findUserDetailsByEmail(@Param("email") String email);
}
