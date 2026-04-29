package com.shortify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API Response DTO
 * Matches Go backend response format
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse<T> {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("payload")
    private T payload;

    public static <T> AppResponse<T> success(T payload) {
        return AppResponse.<T>builder()
                .success(true)
                .payload(payload)
                .build();
    }
}
