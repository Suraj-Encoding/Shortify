package com.shortify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API Error Response DTO
 * Matches Go backend error response format
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppError {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("error")
    private String error;

    @JsonProperty("status_code")
    private int statusCode;

    public static AppError of(String error, int statusCode) {
        return AppError.builder()
                .success(false)
                .error(error)
                .statusCode(statusCode)
                .build();
    }
}
