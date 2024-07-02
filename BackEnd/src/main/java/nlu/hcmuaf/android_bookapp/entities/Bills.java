package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import nlu.hcmuaf.android_bookapp.enums.EBillStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Bills")
public class Bills implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "billId")
  private long billId;

  @Column(name = "totalPrice")
  private double totalPrice;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private EBillStatus status;

  @Column(name = "deliveryDate")
  private LocalDate deliveryDate;

  @ManyToOne
  @JoinColumn(name = "userId")
  private Users user;

  @ManyToOne
  @JoinColumn(name = "paymentId")
  private Payments payments;

  @ManyToOne
  @JoinColumn(name = "discountId")
  private Discounts discounts;

  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
  private Set<BillDetails> billDetails;

  @Column(name = "receiptDate")
  private LocalDate receiptDate;

  @ManyToOne
  @JoinColumn(name = "addressId")
  private Addresses address;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bills bills = (Bills) o;
    return billId == bills.billId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(billId);
  }
}
