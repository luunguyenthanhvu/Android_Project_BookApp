package nlu.hcmuaf.android_bookapp.repositories;

import nlu.hcmuaf.android_bookapp.entities.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bills, Long> {

}
