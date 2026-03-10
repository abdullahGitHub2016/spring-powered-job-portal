package com.abdullah.bdjobs_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdjobsBackendApplication { // <-- Use THIS name below

    public static void main(String[] args) {
        // This MUST be the first line in the main method
        System.setProperty("spring.security.strategy", "MODE_INHERITABLETHREADLOCAL");
        SpringApplication.run(BdjobsBackendApplication.class, args);
    }
}