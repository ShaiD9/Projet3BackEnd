package com.openclassroom.apiRentalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.openclassroom.apiRentalApp.repositories")
@EntityScan(basePackages = "com.openclassroom.apiRentalApp.models")
public class ApiRentalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRentalAppApplication.class, args);
    }
}