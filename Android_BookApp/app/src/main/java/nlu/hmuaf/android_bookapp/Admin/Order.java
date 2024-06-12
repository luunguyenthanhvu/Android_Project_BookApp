package nlu.hmuaf.android_bookapp.Admin;

public class Order {
    private String userName;
    private String productName;
    private String productImageUrl;
    private int quantity;
    private String price;
    private String total;
    private String status;

    private static String productCode;

    public Order(String userName, String productName, String productImageUrl, int quantity, String price, String total, String status, String productCode) {
        this.userName = userName;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.status = status;
        this.productCode = productCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
