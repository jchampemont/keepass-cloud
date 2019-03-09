package com.jeanchampemont.keepasscloud;

import com.jeanchampemont.keepasscloud.api.CreatePasswordDatabaseRequest;
import com.jeanchampemont.keepasscloud.api.PasswordDatabase;
import com.jeanchampemont.keepasscloud.util.Error;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class KeepassCloudApplicationTests {

    @Inject
    TestRestTemplate restTemplate;

    @MockBean
    Clock clock;

    @Before
    public void setup() {
        when(clock.instant()).thenReturn(Instant.ofEpochSecond(609948000));
    }

    @Test
    public void canCreateAPasswordDatabase() {
        var request = new CreatePasswordDatabaseRequest();
        request.setName("test");

        var response = restTemplate.postForEntity("/password-database", request, PasswordDatabase.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotBlank();
        assertThat(response.getBody().getName()).isEqualTo(request.getName());
        assertThat(response.getBody().getCreated()).isEqualTo(Instant.ofEpochSecond(609948000));
        assertThat(response.getBody().getModified()).isNull();
    }

    @Test
    public void cannotCreateAPasswordDatabaseWithSameNameTwice() {
        var request = new CreatePasswordDatabaseRequest();
        request.setName("existing");

        restTemplate.postForEntity("/password-database", request, PasswordDatabase.class);
        request.setName("exIsTinG");
        var response = restTemplate.postForEntity("/password-database", request, Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(1);
        assertThat(response.getBody().getMessage()).contains("is already used");
    }

}
