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
import lombok.Data;

@Data
@Entity(name = "Addresses")
public class Addresses implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addressId")
  private long addressId;

  @Column(name = "city")
  private String city;

  @Column(name = "district")
  private String district;

  @Column(name = "ward")
  private String ward;

  @Column(name = "street")
  private String street;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private Set<UserAddresses> userAddresses;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private Set<Shipments> shipments;
}
