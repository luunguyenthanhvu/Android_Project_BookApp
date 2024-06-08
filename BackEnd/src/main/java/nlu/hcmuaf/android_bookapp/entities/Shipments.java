package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Shipments")
public class Shipments implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shipmentId")
  private long shipmentId;

  @Column(name = "dateAdded")
  private LocalDate dateAdded;

  @Column(name = "available")
  private boolean available;

  @Column(name = "totalQuantity")
  private int totalQuantity;

  @ManyToOne
  @JoinColumn(name = "userId")
  private Users user;

  @ManyToOne
  @JoinColumn(name = "addressId")
  private Addresses address;

  @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
  private Set<ShipmentDetails> shipmentDetails;

  @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
  private Set<ShipmentTransactions> shipmentTransactions;

  @Override
  public int hashCode() {
    return Objects.hash(shipmentId, address, user, available, dateAdded, totalQuantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Shipments that = (Shipments) o;
    return available == that.available &&
        totalQuantity == that.totalQuantity &&
        Objects.equals(shipmentId, that.shipmentId) &&
        Objects.equals(address, that.address) &&
        Objects.equals(user, that.user) &&
        Objects.equals(dateAdded, that.dateAdded);
  }
}
