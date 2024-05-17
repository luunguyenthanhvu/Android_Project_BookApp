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
@Entity(name = "Publish_Company")
public class PublishCompany implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "publishCompanyId")
  private long publishCompanyId;

  @Column(name = "companyName")
  private String companyName;

  @OneToMany(mappedBy = "publishCompany", cascade = CascadeType.ALL)
  private Set<Books> books;
}
