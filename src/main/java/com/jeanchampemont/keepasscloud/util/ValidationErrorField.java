package com.jeanchampemont.keepasscloud.util;

public class ValidationErrorField {
    private String field;

    private String message;

    ValidationErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
