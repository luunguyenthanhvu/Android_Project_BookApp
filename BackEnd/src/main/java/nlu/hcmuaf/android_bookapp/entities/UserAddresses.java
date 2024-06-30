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
@Entity(name = "User_Addresses")
public class UserAddresses implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "userId")
  private UserDetails userDetails;

  @Id
  @ManyToOne
  @JoinColumn(name = "addressId")
  private Addresses address;

  @Column(name = "mainAddress")
  private boolean mainAddress;

}
