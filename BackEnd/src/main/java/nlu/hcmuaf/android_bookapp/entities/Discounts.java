package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
@Entity(name = "Discounts")
public class Discounts implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "discountId")
  private long discount;

  @Column(name = "percent")
  private double percent;

  @Column(name = "expireDate")
  private LocalDate expireDate;

  @Column(name = "description")
  private String description;

  @Column(name = "condition")
  private String condition;

  @Column(name = "status")
  private String status;

  @OneToMany(mappedBy = "discounts", cascade = CascadeType.ALL)
  private Set<Books> books;

}
