package nlu.hcmuaf.android_bookapp.repositories;

import jakarta.transaction.Transactional;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

  Optional<Users> findUsersByUsername(String username);

  Optional<Users> findUsersByUserId(long id);

  @Query("SELECT u FROM Users u JOIN FETCH u.userDetails ud JOIN FETCH u.roles ur WHERE ud.email = :email")
  Optional<Users> findAllInfoByEmail(@Param("email") String email);

  @Query("SELECT u from Users u JOIN FETCH u.roles WHERE u.userId = :id")
  Optional<Users> findUsersByUserIdWithRole(@Param("id") long id);

  @Modifying
  @Transactional
  @Query("UPDATE Users  u SET u.verified = true WHERE u.userId = :id")
  int updateUserVerified(@Param("id") long id);

}
