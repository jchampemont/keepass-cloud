package com.jeanchampemont.keepasscloud.service;

import com.jeanchampemont.keepasscloud.db.model.PasswordDatabase;
import com.jeanchampemont.keepasscloud.db.repository.PasswordDatabaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordDatabaseServiceTest {

    @Mock
    PasswordDatabaseRepository repository;

    @Mock
    Clock clock;

    @InjectMocks
    PasswordDatabaseService service;

    @Test
    public void canCreateAPasswordDatabase() {
        when(clock.instant()).thenReturn(Instant.ofEpochSecond(609948000));
        var db = new PasswordDatabase("test", Instant.ofEpochSecond(609948000));
        when(repository.save(any())).thenReturn(db);

        var result = service.create("test");

        assertThat(result).isEqualTo(db);
    }

    @Test
    public void existsIsTrueWhenPasswordDatabaseExists() {
        when(repository.findByNameIgnoreCase("slug")).thenReturn(new PasswordDatabase());

        var result = service.exists("slug");

        assertThat(result).isTrue();
    }

    @Test
    public void existsIsFalseWhenPasswordDatabaseExists() {
        when(repository.findByNameIgnoreCase("slug")).thenReturn(null);

        var result = service.exists("slug");

        assertThat(result).isFalse();
    }
}
