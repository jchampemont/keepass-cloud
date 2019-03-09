package com.jeanchampemont.keepasscloud.util;

public class Error {
    private int code;

    private String message;

    public Error() {
    }

    Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
