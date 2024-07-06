package com.example.hamro_barber.helper;

public enum UserRole {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    BARBER("BARBER"),
    CUSTOMER("CUSTOMER");

    final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
