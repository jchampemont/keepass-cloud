package com.jeanchampemont.keepasscloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class KeepassCloudApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KeepassCloudApplication.class, args);
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
