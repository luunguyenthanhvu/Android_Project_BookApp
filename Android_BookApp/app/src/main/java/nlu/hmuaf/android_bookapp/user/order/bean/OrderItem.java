package nlu.hmuaf.android_bookapp.user.order.bean;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private int resourceid;
    private String productName;
    private int quantity;
    private String productPrice;

    public OrderItem(int resourceid, String productName, int quantity, String productPrice) {
        this.resourceid = resourceid;
        this.productName = productName;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}