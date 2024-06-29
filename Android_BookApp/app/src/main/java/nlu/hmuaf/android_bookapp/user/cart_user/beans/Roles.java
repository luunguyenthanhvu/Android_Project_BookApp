package nlu.hmuaf.android_bookapp.user.cart_user.beans;

import java.io.Serializable;

import nlu.hmuaf.android_bookapp.user.cart_user.enums.ERole;

public class Roles implements Serializable {
    private long roleId;
    private ERole roleName;

    public Roles(long roleId, ERole roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    public Roles(){

    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public ERole getRoleName() {
        return roleName;
    }

    public void setRoleName(ERole roleName) {
        this.roleName = roleName;
    }
}
