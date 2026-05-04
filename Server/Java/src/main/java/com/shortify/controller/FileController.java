package com.shortify.controller;

import com.shortify.dto.AppError;
import com.shortify.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * File Controller - Serves static files
 * Route: /file
 */
@Slf4j
@RestController
public class FileController {

    /**
     * Serve File
     * GET /file?sub_path=...
     */
    @GetMapping("/file")
    public ResponseEntity<?> serveFile(@RequestParam("sub_path") String subPath) {
        if (subPath == null || subPath.trim().isEmpty()) {
            throw new AppException(
                    "🚫 Invalid file requested to shortify: Empty file sub-path provided",
                    HttpStatus.BAD_REQUEST);
        }

        log.info("🔗 File Sub-Path: {}", subPath);

        try {
            Resource resource = new ClassPathResource("public/" + subPath.trim());
            
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            byte[] content = Files.readAllBytes(Path.of(resource.getURI()));
            
            // Determine content type based on file extension
            String contentType = determineContentType(subPath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(content);
        } catch (IOException e) {
            log.error("Failed to serve file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AppError.of("Failed to serve file", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    /**
     * Determine content type based on file extension
     */
    private String determineContentType(String filePath) {
        String lowerPath = filePath.toLowerCase();
        
        if (lowerPath.endsWith(".html") || lowerPath.endsWith(".htm")) {
            return "text/html";
        } else if (lowerPath.endsWith(".css")) {
            return "text/css";
        } else if (lowerPath.endsWith(".js")) {
            return "application/javascript";
        } else if (lowerPath.endsWith(".json")) {
            return "application/json";
        } else if (lowerPath.endsWith(".png")) {
            return "image/png";
        } else if (lowerPath.endsWith(".jpg") || lowerPath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerPath.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerPath.endsWith(".svg")) {
            return "image/svg+xml";
        } else if (lowerPath.endsWith(".ico")) {
            return "image/x-icon";
        } else if (lowerPath.endsWith(".webp")) {
            return "image/webp";
        } else if (lowerPath.endsWith(".pdf")) {
            return "application/pdf";
        } else if (lowerPath.endsWith(".xml")) {
            return "application/xml";
        } else if (lowerPath.endsWith(".txt")) {
            return "text/plain";
        }
        
        return "application/octet-stream";
    }
}
