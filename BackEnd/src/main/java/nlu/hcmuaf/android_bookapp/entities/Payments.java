package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import nlu.hcmuaf.android_bookapp.enums.EPaymentMethod;

@Data
@Entity(name = "Payments")
public class Payments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "paymentId")
  private long paymentId;

  @Column(name = "paymentMethod")
  @Enumerated(EnumType.STRING)
  private EPaymentMethod paymentMethod;

}
