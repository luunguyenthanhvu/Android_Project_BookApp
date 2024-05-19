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
@Entity(name = "Books")
public class Books implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bookId")
  private long bookId;

  @Column(name = "code", unique = true)
  private String code;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private double price;

  @Column(name = "publicationDate")
  private LocalDate publicationDate;

  @Column(name = "author")
  private String author;

  @Column(name = "thumbnail")
  private String thumbnail;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private Set<BookImages> bookImages;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private Set<BookGenres> bookGenres;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private Set<CartUsers> cartUsers;

  @ManyToOne
  @JoinColumn(name = "discountId", nullable = true)
  private Discounts discounts;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private Set<BillDetails> billDetails;

  @ManyToOne
  @JoinColumn(name = "publishCompanyId")
  private PublishCompany publishCompany;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private Set<ShipmentDetails> shipmentDetails;
}
