package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.Payments;
import nlu.hcmuaf.android_bookapp.enums.EPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long> {

  @Query("SELECT p FROM Payments p")
  List<Payments> getAllBy();

  @Query("SELECT p FROM Payments p WHERE p.paymentMethod = :paymentMethod")
  Optional<Payments> findAllByPaymentMethod(@Param("paymentMethod") EPaymentMethod paymentMethod);
}
