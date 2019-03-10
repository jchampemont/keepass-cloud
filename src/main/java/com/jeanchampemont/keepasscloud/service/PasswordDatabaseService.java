package com.jeanchampemont.keepasscloud.service;

import com.jeanchampemont.keepasscloud.db.model.PasswordDatabase;
import com.jeanchampemont.keepasscloud.db.repository.PasswordDatabaseRepository;
import com.jeanchampemont.keepasscloud.features.passworddatabase.error.NameAlreadyUsed;
import com.jeanchampemont.keepasscloud.features.passworddatabase.error.PasswordDatabaseNotFound;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Clock;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PasswordDatabaseService {

    private PasswordDatabaseRepository repository;

    private Clock clock;

    @Inject
    public PasswordDatabaseService(PasswordDatabaseRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public PasswordDatabase create(String name) {
        PasswordDatabase passwordDatabase = new PasswordDatabase(name, clock.instant());
        return repository.save(passwordDatabase);
    }

    public PasswordDatabase update(UUID id, String name) throws PasswordDatabaseNotFound, NameAlreadyUsed {
        PasswordDatabase sameName = repository.findByNameIgnoreCase(name);

        if(sameName != null && !sameName.getId().equals(id)) {
            throw new NameAlreadyUsed(name);
        }

        var existing = repository.findById(id).orElseThrow(() -> new PasswordDatabaseNotFound(id.toString()));
        existing.setName(name);
        existing.setModified(clock.instant());
        return repository.save(existing);
    }

    public boolean exists(String name) {
        return repository.findByNameIgnoreCase(name) != null;
    }

    public Stream<PasswordDatabase> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

    public Optional<PasswordDatabase> get(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
