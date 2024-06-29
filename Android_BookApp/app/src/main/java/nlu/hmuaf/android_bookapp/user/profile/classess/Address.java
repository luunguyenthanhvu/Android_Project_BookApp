package nlu.hmuaf.android_bookapp.user.profile.classess;

import java.io.Serializable;

public class Address implements Serializable {
    private int addressId;
    private String city;
    private String district;
    private String ward;
    private String street;
    private boolean isDefault;
    private User user;

    // Constructor
    public Address(int addressId, String city, String district, String ward, String street, boolean isDefault) {
        this.addressId = addressId;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.isDefault = isDefault;
    }

    public Address() {

    }

    // Getters and setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
