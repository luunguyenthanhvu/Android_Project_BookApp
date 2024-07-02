package nlu.hmuaf.android_bookapp.Admin.ManageProduct;

public class Product  {
    private String image;
    private String publishCompany;
    private String status;
    private String price;
    private String authorName;
    private String productName;

    public Product(String image, String publishCompany, String status,String price, String authorName, String productName) {
        this.image = image;
        this.publishCompany = publishCompany;
        this.status = status;
        this.price = price;
        this.authorName = authorName;
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishCompany() {
        return publishCompany;
    }

    public void setPublishCompany(String publishCompany) {
        this.publishCompany = publishCompany;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}