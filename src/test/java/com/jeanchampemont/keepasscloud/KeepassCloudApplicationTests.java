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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

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

    @Test
    public void canListAllPasswordDatabase() {
        var request = new CreatePasswordDatabaseRequest();
        request.setName("listing");
        restTemplate.postForEntity("/password-database", request, PasswordDatabase.class);

        var response = restTemplate.exchange("/password-database", HttpMethod.GET, null, new ParameterizedTypeReference<List<PasswordDatabase>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().stream().map(PasswordDatabase::getName)).contains("listing");
    }

    @Test
    public void canGetAPasswordDatabase() {
        var request = new CreatePasswordDatabaseRequest();
        request.setName("toGet");
        var id = restTemplate.postForEntity("/password-database", request, PasswordDatabase.class).getBody().getId();

        var response = restTemplate.getForEntity("/password-database/" + id, PasswordDatabase.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo("toGet");
        assertThat(response.getBody().getCreated()).isEqualTo(Instant.ofEpochSecond(609948000));
        assertThat(response.getBody().getModified()).isNull();
    }

    @Test
    public void cannotGetUnexistingPasswordDatabase() {
        var response = restTemplate.getForEntity("/password-database/de5a6ab8-1c43-4ac8-8682-e7509bc8c23b", Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(2);
        assertThat(response.getBody().getMessage()).contains("was not found");
    }

    @Test
    public void cannotGetPasswordDatabaseWithInvalidUUID() {
        var response = restTemplate.getForEntity("/password-database/invalid-uuid", Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(2);
        assertThat(response.getBody().getMessage()).contains("was not found");
    }

    @Test
    public void canDeleteAPasswordDatabase() {
        var request = new CreatePasswordDatabaseRequest();
        request.setName("toDelete");
        var id = restTemplate.postForEntity("/password-database", request, PasswordDatabase.class).getBody().getId();

        var response = restTemplate.exchange("/password-database/" + id, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var response2 = restTemplate.getForEntity("/password-database/" + id, Error.class);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response2.getBody()).isNotNull();
        assertThat(response2.getBody().getCode()).isEqualTo(2);
        assertThat(response2.getBody().getMessage()).contains("was not found");
    }

    @Test
    public void cannotDeleteUnexistingPasswordDatabase() {
        var response = restTemplate.exchange("/password-database/de5a6ab8-1c43-4ac8-8682-e7509bc8c23b", HttpMethod.DELETE, null, Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(2);
        assertThat(response.getBody().getMessage()).contains("was not found");
    }

    @Test
    public void cannotDeletePasswordDatabaseWithInvalidUUID() {
        var response = restTemplate.exchange("/password-database/invalid-uuid", HttpMethod.DELETE, null, Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(2);
        assertThat(response.getBody().getMessage()).contains("was not found");
    }
}
