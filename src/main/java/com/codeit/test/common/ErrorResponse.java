package com.codeit.test.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;
    private String error;
    private String message;

    public static ErrorResponse of(String error, String message) {
        return new ErrorResponse(false, error, message);
    }
}
