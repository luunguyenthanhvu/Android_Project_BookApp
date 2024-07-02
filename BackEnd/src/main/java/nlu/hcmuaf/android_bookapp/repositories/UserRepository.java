package nlu.hcmuaf.android_bookapp.repositories;

import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

  @Query("SELECT u FROM Users u WHERE u.username = :username")
  Optional<Users> findUsersByUsername(@Param("username") String username);

  Optional<Users> findUsersByUserId(long id);

  @Query("SELECT u FROM Users u JOIN FETCH u.userDetails ud JOIN FETCH u.roles ur WHERE ud.email = :email")
  Optional<Users> findAllInfoByEmail(@Param("email") String email);

  @Query("SELECT u FROM Users u JOIN FETCH u.userDetails ud JOIN FETCH u.roles ur WHERE ud.userId = :userId")
  Optional<Users> findAllInfoById(@Param("userId") long userId);

  @Query("SELECT u from Users u JOIN FETCH u.roles WHERE u.userId = :id")
  Optional<Users> findUsersByUserIdWithRole(@Param("id") long id);

}
