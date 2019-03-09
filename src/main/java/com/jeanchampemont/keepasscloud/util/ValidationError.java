package com.jeanchampemont.keepasscloud.util;

import java.util.Collections;
import java.util.List;

public class ValidationError {
    private int code = 0;

    private String message = "Some fields are invalid";

    private List<ValidationErrorField> errors;

    ValidationError(List<ValidationErrorField> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationErrorField> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
