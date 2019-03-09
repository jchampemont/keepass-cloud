package com.jeanchampemont.keepasscloud.api;

import java.time.Instant;

public class PasswordDatabase {
    private String id;

    private String name;

    private Instant created;

    private Instant modified;

    public PasswordDatabase() {
    }

    public PasswordDatabase(String id, String name, Instant created, Instant modified) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.modified = modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }
}
