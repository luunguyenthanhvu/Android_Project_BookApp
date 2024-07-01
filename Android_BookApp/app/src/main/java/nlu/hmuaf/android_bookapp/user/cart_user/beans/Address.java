package nlu.hmuaf.android_bookapp.user.cart_user.beans;

import java.io.Serializable;

public class Address implements Serializable {

    private long addressId;
    private String addressDetails;

    public Address(long addressId, String addressDetails) {
        this.addressId = addressId;
        this.addressDetails = addressDetails;
    }
    public Address(){

    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }
}
