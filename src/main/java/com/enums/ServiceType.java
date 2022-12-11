package com.enums;

public enum ServiceType {
    RESTAURANT("R"),
    CAFE("C"),
    SNACK("S");

    public final String value;

    private ServiceType(String value){
        this.value = value;
    }



}
