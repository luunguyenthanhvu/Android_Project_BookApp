package nlu.hcmuaf.android_bookapp.repositories;

import nlu.hcmuaf.android_bookapp.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

  public Users findUsersByUsername(String username);
}
