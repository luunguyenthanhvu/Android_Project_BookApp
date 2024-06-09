package nlu.hcmuaf.android_bookapp.repositories;

import nlu.hcmuaf.android_bookapp.entities.ShipmentTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentTransactionRepository extends JpaRepository<ShipmentTransactions, Long> {

}
