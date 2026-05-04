package com.shortify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Shortify - Modern URL Shortener
 * Main Application Entry Point
 */
@SpringBootApplication
public class ShortifyApplication {

    public static void main(String[] args) {
        System.out.println("#----------| 🚀 Welcome to Shortify - Modern URL Shortener 🚀 |----------#");
        SpringApplication.run(ShortifyApplication.class, args);
        System.out.println("🕸️  Server Connected!");
        System.out.println("🕸️  Database Connected!");
        System.out.println("🚀 Server is running...");
    }
}
