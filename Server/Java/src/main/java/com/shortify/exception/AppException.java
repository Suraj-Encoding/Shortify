package com.shortify.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Application Exception
 */
@Getter
public class AppException extends RuntimeException {

    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public AppException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
