package com.data.enums;

public enum UserRole {

    SUPERADMIN("S"),
    ADMIN("A"),
    MANAGER("M");

    public final String value;

    private UserRole(String value){
        this.value = value;
    }

}
