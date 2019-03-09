package com.jeanchampemont.keepasscloud.db.repository;

import com.jeanchampemont.keepasscloud.db.model.PasswordDatabase;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PasswordDatabaseRepository extends CrudRepository<PasswordDatabase, UUID> {
    PasswordDatabase findByNameIgnoreCase(String name);
}
