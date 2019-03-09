package com.jeanchampemont.keepasscloud.configuration;

import com.jeanchampemont.keepasscloud.features.passworddatabase.PasswordDatabaseFeature;
import com.jeanchampemont.keepasscloud.util.ConstraintViolationMapper;
import com.jeanchampemont.keepasscloud.util.RestExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(RestExceptionMapper.class);
        register(ConstraintViolationMapper.class);

        register(PasswordDatabaseFeature.class);
    }
}
