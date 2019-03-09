package com.jeanchampemont.keepasscloud.service;

import com.jeanchampemont.keepasscloud.db.model.PasswordDatabase;
import com.jeanchampemont.keepasscloud.db.repository.PasswordDatabaseRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Clock;
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

    public boolean exists(String name) {
        return repository.findByNameIgnoreCase(name) != null;
    }

    public Stream<PasswordDatabase> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }
}
