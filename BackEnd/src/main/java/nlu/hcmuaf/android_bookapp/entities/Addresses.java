package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Addresses")
public class Addresses implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addressId")
  private long addressId;

  @Column(name = "addressDetails")
  private String addressDetails;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private Set<UserAddresses> userAddresses;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private Set<Shipments> shipments;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private Set<Bills> bills;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (getClass() != o.getClass()) {
      return false;
    }

    return false;
  }
}
