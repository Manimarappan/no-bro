package com.property.no_bro.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorStructure {
    private String errorCode;
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;

    public ErrorStructure(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }
}