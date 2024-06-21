package nlu.hcmuaf.android_bookapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_bookapp.enums.EBookFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Book_Details")
public class BookDetails {

  @Id
  @Column(name = "bookId")
  private long bookId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "bookId")
  private Books book;

  @ManyToOne
  @JoinColumn(name = "publishCompanyId")
  private PublishCompany publishCompany;

  @Column(name = "bookFormat")
  @Enumerated(EnumType.STRING)
  private EBookFormat eBookFormat;

  @Column(name = "author")
  private String author;

  @Column(name = "size")
  private String size;

  @Column(name = "numPage")
  private int numPage;

  @Override
  public String toString() {
    return ("Loại sản phẩm : " + eBookFormat.toString() + "\n" +
        "Kích thước : " + size + "\n" +
        "Số trang : " + numPage + "\n" +
        "Tác giả : " + author + "\n");
  }
}
