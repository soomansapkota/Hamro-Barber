package com.example.hamro_barber.constants;

public enum FileType {
    USER_IMAGES("user_images"),
    DOCUMENTS("documents"),
    PRODUCT_IMAGES("product_images"),
    FILES("files");

    public final String value;
    FileType(String value) { this.value = value; }

    public String getValue() {
        return value;
    }
}
