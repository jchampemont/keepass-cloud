package com.jeanchampemont.keepasscloud.features.passworddatabase.error;

import com.jeanchampemont.keepasscloud.util.RestException;

import javax.ws.rs.core.Response.Status;

public class NameAlreadyUsed extends RestException {
    public NameAlreadyUsed(String name) {
        super(Status.BAD_REQUEST, 1, "The name '" + name + "' is already used.");
    }
}
