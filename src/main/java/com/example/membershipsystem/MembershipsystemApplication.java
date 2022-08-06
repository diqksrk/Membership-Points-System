package com.example.membershipsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MembershipsystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(MembershipsystemApplication.class, args);
    }
}
