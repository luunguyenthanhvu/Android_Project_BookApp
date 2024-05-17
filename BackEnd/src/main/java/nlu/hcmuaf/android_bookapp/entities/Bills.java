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
import java.util.Set;
import lombok.Data;
import nlu.hcmuaf.android_bookapp.enums.EBillStatus;

@Data
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
}
