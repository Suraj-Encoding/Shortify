package com.shortify.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Home Controller - Serves the home page
 * Route: /
 */
@Slf4j
@RestController
public class HomeController {

    /**
     * Serve Home Page
     * GET /
     */
    @GetMapping("/")
    public ResponseEntity<String> serveHomePage() {
        try {
            Resource resource = new ClassPathResource("public/html/index.html");
            String content = Files.readString(Path.of(resource.getURI()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(content);
        } catch (IOException e) {
            log.error("Failed to serve home page: {}", e.getMessage());
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body("<html><body><h1>Welcome to Shortify - Modern URL Shortener</h1></body></html>");
        }
    }
}
