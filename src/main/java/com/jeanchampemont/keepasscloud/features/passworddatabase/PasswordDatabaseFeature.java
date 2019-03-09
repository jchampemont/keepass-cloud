package com.jeanchampemont.keepasscloud.features.passworddatabase;

import com.jeanchampemont.keepasscloud.api.CreatePasswordDatabaseRequest;
import com.jeanchampemont.keepasscloud.api.PasswordDatabase;
import com.jeanchampemont.keepasscloud.features.passworddatabase.error.NameAlreadyUsed;
import com.jeanchampemont.keepasscloud.service.PasswordDatabaseService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PasswordDatabase> getAll() {
        return service.getAll().map(this::map).collect(Collectors.toList());
    }

    private PasswordDatabase map(com.jeanchampemont.keepasscloud.db.model.PasswordDatabase passwordDatabase) {
        return new PasswordDatabase(passwordDatabase.getId().toString(), passwordDatabase.getName(), passwordDatabase.getCreated(), passwordDatabase.getModified());
    }
}
