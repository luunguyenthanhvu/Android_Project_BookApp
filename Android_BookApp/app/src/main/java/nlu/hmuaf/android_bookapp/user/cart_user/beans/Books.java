package nlu.hmuaf.android_bookapp.user.cart_user.beans;

import java.io.Serializable;
import java.sql.Date;

public class Books implements Serializable {
    private long bookId;
    private int discountId;
    private int publishCompanyId;
    private String code, title,description;
    private double price;
    private Date publicationDate;

    private String author;
  private String thumbnail;

    public Books(long bookId, int discountId, int publishCompanyId, String code, String title, String description, double price, Date publicationDate, String author, String thumbnail) {
        this.bookId = bookId;
        this.discountId = discountId;
        this.publishCompanyId = publishCompanyId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.price = price;
        this.publicationDate = publicationDate;
        this.author = author;
        this.thumbnail = thumbnail;
    }
    public Books(){

    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getPublishCompanyId() {
        return publishCompanyId;
    }

    public void setPublishCompanyId(int publishCompanyId) {
        this.publishCompanyId = publishCompanyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
