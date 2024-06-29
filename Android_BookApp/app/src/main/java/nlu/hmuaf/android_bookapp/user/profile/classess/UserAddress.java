package nlu.hmuaf.android_bookapp.user.profile.classess;

public class UserAddress {
    private int userId;
    private int addressId;

    // Constructor
    public UserAddress(int userId, int addressId) {
        this.userId = userId;
        this.addressId = addressId;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
