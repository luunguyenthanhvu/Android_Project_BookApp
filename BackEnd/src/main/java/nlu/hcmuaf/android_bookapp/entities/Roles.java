package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Data;
import nlu.hcmuaf.android_bookapp.enums.ERole;

@Data
@Entity(name = "Roles")
public class Roles implements Serializable {
  @Id
  @Column(name = "roleId")
  private long roleId;

  @Column(name = "roleName")
  @Enumerated(EnumType.STRING)
  private ERole roleName;
}
