package com.example.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the balance management application.
 */
@SpringBootApplication
public class BalanceApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args startup arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BalanceApplication.class, args);
    }
}
