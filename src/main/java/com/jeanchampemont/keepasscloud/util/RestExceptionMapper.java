package com.jeanchampemont.keepasscloud.util;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    @Override
    public Response toResponse(RestException exception) {
        return Response.status(exception.getStatus())
                .entity(new Error(exception.getErrorCode(), exception.getMessage()))
                .build();
    }
}

