package ru.jonnykea.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RestaurantsVotingSystem {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsVotingSystem.class, args);
    }
}
