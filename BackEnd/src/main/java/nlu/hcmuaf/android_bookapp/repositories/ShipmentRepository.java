package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShipmentRepository extends JpaRepository<Shipments, Long> {

  @Query("SELECT s FROM Shipments s")
  List<Shipments> getAllBy();
}
