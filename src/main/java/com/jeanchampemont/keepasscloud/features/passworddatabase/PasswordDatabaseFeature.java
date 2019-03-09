package com.jeanchampemont.keepasscloud.features.passworddatabase;

import com.jeanchampemont.keepasscloud.api.CreatePasswordDatabaseRequest;
import com.jeanchampemont.keepasscloud.api.PasswordDatabase;
import com.jeanchampemont.keepasscloud.features.passworddatabase.error.NameAlreadyUsed;
import com.jeanchampemont.keepasscloud.service.PasswordDatabaseService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/password-database")
public class PasswordDatabaseFeature {

    private PasswordDatabaseService service;

    @Inject
    public PasswordDatabaseFeature(PasswordDatabaseService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PasswordDatabase create(@Valid CreatePasswordDatabaseRequest request) throws NameAlreadyUsed {
        if (service.exists(request.getName())) {
            throw new NameAlreadyUsed(request.getName());
        }
        return map(service.create(request.getName()));
    }

    private PasswordDatabase map(com.jeanchampemont.keepasscloud.db.model.PasswordDatabase passwordDatabase) {
        return new PasswordDatabase(passwordDatabase.getId().toString(), passwordDatabase.getName(), passwordDatabase.getCreated(), passwordDatabase.getModified());
    }
}
