package com.jeanchampemont.keepasscloud.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordDatabaseRequest {
    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
