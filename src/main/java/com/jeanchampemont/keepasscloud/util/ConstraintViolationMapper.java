package com.jeanchampemont.keepasscloud.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<ValidationErrorField> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = null;
            Iterator<Path.Node> it = violation.getPropertyPath().iterator();
            while(it.hasNext()) {
                fieldName = it.next().getName();
            }
            messages.add(new ValidationErrorField(fieldName, violation.getMessage()));
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationError(messages)).build();
    }
}

