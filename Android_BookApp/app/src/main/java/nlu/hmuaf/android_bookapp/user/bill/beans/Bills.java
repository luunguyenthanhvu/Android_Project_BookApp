
package nlu.hmuaf.android_bookapp.user.bill.beans;


import java.io.Serializable;
import java.time.LocalDate;

import nlu.hmuaf.android_bookapp.user.bill.enums.EBillStatus;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Discounts;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Payments;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Users;


public class Bills implements Serializable {
    private long billId;
    private double totalPrice;
    private EBillStatus status;
    private LocalDate deliveryDate;
    private LocalDate receiptDate;
    private Users user;
    private Payments payments;
    private Discounts discounts;


    public Bills(long billId, double totalPrice, EBillStatus status, LocalDate deliveryDate, LocalDate receiptDate, Users user, Payments payments, Discounts discounts) {
        this.billId = billId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.receiptDate = receiptDate;
        this.user = user;
        this.payments = payments;
        this.discounts = discounts;
    }
    public Bills(){

    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public EBillStatus getStatus() {
        return status;
    }

    public void setStatus(EBillStatus status) {
        this.status = status;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    public Discounts getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Discounts discounts) {
        this.discounts = discounts;
    }
}
