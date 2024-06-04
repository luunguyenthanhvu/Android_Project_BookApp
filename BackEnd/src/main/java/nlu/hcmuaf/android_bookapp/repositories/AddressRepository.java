package nlu.hcmuaf.android_bookapp.repositories;

import nlu.hcmuaf.android_bookapp.entities.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Addresses, Long> {

}
