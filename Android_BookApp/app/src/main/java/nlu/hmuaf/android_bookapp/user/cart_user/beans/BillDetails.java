
package nlu.hmuaf.android_bookapp.user.cart_user.beans;


import java.io.Serializable;



public class BillDetails implements Serializable {
    private Books book;
    private Bills bill;
    private int quantity;

    public BillDetails(Books book, Bills bill, int quantity) {
        this.book = book;
        this.bill = bill;
        this.quantity = quantity;
    }
    public BillDetails(){

    }

    public Books getBook() {
        return book;
    }

    public Bills getBill() {
        return bill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public void setBill(Bills bill) {
        this.bill = bill;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
