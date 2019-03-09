package com.jeanchampemont.keepasscloud.util;

import javax.ws.rs.core.Response.Status;

public abstract class RestException extends Exception {
    private Status status;

    private int errorCode;

    private String message;

    public RestException(Status status, int errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
