package nlu.hmuaf.android_bookapp.user.order.bean;

import java.io.Serializable;

public class Order implements Serializable {
    private String userName;
    private String productName;
    private int resourceid;
    private int quantity;
    private String price;
    private String total;
    private String status;

    private String orderId;

    public Order() {

    }
    public Order(String userName, String productName, int resourceid, int quantity, String price, String total, String status, String orderId) {
        this.userName = userName;
        this.productName = productName;
        this.resourceid = resourceid;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.status = status;
        this.orderId = orderId;
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

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
