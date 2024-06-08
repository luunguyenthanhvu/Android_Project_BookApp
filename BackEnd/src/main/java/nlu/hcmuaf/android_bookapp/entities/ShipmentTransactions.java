package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Shipment_Transactions")
public class ShipmentTransactions implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shipmentTransactionId")
  private long id;

  @Column(name = "transactionDate")
  private LocalDate transactionDate;

  @Column(name = "quantityChange")
  private String quantityChange;

  @Column(name = "note")
  private String note;

  @Column(name = "status")
  private String status;

  @ManyToOne
  @JoinColumn(name = "shipmentId")
  private Shipments shipment;
}
