package nlu.hmuaf.android_bookapp.profile;

public class Address {
    private String name;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private boolean isDefault;

    public Address(String name, String phone, String addressLine1, String addressLine2, boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
