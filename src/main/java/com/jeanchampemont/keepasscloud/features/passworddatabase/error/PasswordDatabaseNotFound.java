package com.jeanchampemont.keepasscloud.features.passworddatabase.error;

import com.jeanchampemont.keepasscloud.util.RestException;

import javax.ws.rs.core.Response.Status;

public class PasswordDatabaseNotFound extends RestException {
    public PasswordDatabaseNotFound(String id) {
        super(Status.NOT_FOUND, 2, "The password datbase with id '" + id + "' was not found.");
    }
}
