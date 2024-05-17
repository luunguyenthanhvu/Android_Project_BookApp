package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity(name = "Shipment_Details")
public class ShipmentDetails implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "shipmentId")
  private Shipments shipment;

  @Id
  @ManyToOne
  @JoinColumn(name = "bookId")
  private Books book;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "status")
  private String status;
}
