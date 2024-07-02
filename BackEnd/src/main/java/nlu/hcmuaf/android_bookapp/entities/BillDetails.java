package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Bill_Details")
public class BillDetails implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "bookId")
  private Books book;

  @Id
  @ManyToOne
  @JoinColumn(name = "billId")
  private Bills bill;

  @Column(name = "quantity")
  private int quantity;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BillDetails that = (BillDetails) o;

    if (!book.equals(that.book)) {
      return false;
    }
    return bill.equals(that.bill);
  }

  @Override
  public int hashCode() {
    int result = book.hashCode();
    result = 31 * result + bill.hashCode();
    return result;
  }
}
