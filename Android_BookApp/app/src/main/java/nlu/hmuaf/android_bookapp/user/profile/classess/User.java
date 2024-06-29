package nlu.hmuaf.android_bookapp.user.profile.classess;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private int role;
    private String username;
    private String password;
    private String hash;
    private String createdDate;
    private String firstName;
    private String lastName;
    private String phoneNum;

    // Constructor
    public User(int userId, int role, String username, String password, String hash, String createdDate) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;
        this.hash = hash;
        this.createdDate = createdDate;
    }

    public User() {

    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
