package com.example.hamro_barber.constants;

public enum Category {
    HAIRCUT("HAIRCUT"),
    HAIRSTYLE("HAIRSTYLE"),
    BEARD("BEARD"),
    BEAUTY_SALON("BEAUTY_SALON"),
    NAIL_SALON("NAIL_SALON"),
    WEDDING_MAKEUP("WEDDING_MAKEUP"),
    WEDDING_STYLIST("WEDDING_STYLIST");

    public final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
