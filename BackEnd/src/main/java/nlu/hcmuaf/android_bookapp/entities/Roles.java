package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_bookapp.enums.ERole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Roles")
public class Roles implements Serializable {
  @Id
  @Column(name = "roleId")
  private long roleId;

  @Column(name = "roleName")
  @Enumerated(EnumType.STRING)
  private ERole roleName;
}
