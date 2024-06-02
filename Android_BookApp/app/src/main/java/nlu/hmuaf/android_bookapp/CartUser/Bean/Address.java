package nlu.hmuaf.android_bookapp.CartUser.Bean;

import java.io.Serializable;

public class Address implements Serializable {

    private long addressId;
    private String city;
    private String district;
    private String ward;
    private String street;

    public Address(long addressId, String city, String district, String ward, String street) {
        this.addressId = addressId;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
    }
    public Address(){

    }

    public long getAddressId() {
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
}
