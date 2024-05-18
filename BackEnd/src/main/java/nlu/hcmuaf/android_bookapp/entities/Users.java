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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Users")
public class Users implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private long userId;

  @ManyToOne
  @JoinColumn(name = "roleId", referencedColumnName = "roleId")
  private Roles roles;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "hash")
  private String hash;

  @Column(name = "createdDate")
  private LocalDate createdDate;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private UserDetails userDetails;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Cart cart;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Bills> bills;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Shipments> shipments;
}
