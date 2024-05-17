package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;
import nlu.hcmuaf.android_bookapp.enums.EGender;

@Data
@Entity(name = "User_Details")
public class UserDetails implements Serializable {

  @Id
  @Column(name = "userId")
  private long userId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  private Users user;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "dob")
  private LocalDate dob;

  @Column(name = "phoneNum")
  private String phoneNum;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private EGender gender;

  @Column(name = "img")
  private String img;

  @OneToMany(mappedBy = "userDetails")
  private Set<UserAddresses> userAddresses;
}
