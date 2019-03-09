package com.jeanchampemont.keepasscloud.features.passworddatabase;

import com.jeanchampemont.keepasscloud.api.CreatePasswordDatabaseRequest;
import com.jeanchampemont.keepasscloud.api.PasswordDatabase;
import com.jeanchampemont.keepasscloud.features.passworddatabase.error.NameAlreadyUsed;
import com.jeanchampemont.keepasscloud.features.passworddatabase.error.PasswordDatabaseNotFound;
import com.jeanchampemont.keepasscloud.service.PasswordDatabaseService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PasswordDatabase getOne(@PathParam("id") String id) throws PasswordDatabaseNotFound {
        UUID uuid = readUUIDOrThrow(id);
        return service.get(uuid).map(this::map).orElseThrow(() -> new PasswordDatabaseNotFound(id));
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) throws PasswordDatabaseNotFound {
        UUID uuid = readUUIDOrThrow(id);
        service.get(uuid).orElseThrow(() -> new PasswordDatabaseNotFound(id));
        service.delete(uuid);
    }

    private PasswordDatabase map(com.jeanchampemont.keepasscloud.db.model.PasswordDatabase passwordDatabase) {
        return new PasswordDatabase(passwordDatabase.getId().toString(), passwordDatabase.getName(), passwordDatabase.getCreated(), passwordDatabase.getModified());
    }

    private UUID readUUIDOrThrow(String id) throws PasswordDatabaseNotFound {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new PasswordDatabaseNotFound(id);
        }
        return uuid;
    }
}
