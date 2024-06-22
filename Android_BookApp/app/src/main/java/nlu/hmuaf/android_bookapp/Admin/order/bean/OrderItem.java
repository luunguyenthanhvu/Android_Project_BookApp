package nlu.hmuaf.android_bookapp.Admin.order.bean;

public class OrderItem {
    private String productName;
    private String productPrice;

    public OrderItem(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }
}