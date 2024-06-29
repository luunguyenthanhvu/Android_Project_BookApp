package nlu.hmuaf.android_bookapp.user.cart_user.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class Users implements Serializable {
    private long userId;
    private Roles roles;
    private String username;
    private String password;
    private LocalDate createdDate;

    public Users(long userId, Roles roles, String username, String password, LocalDate createdDate) {
        this.userId = userId;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
    }
    public Users(){

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
