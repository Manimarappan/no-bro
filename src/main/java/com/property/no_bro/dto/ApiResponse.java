package com.property.no_bro.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int statusCode;

    public ApiResponse(boolean success, String message, T data, int statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public static <T> ApiResponse<T> success(T data, String message, int statusCode) {
        return new ApiResponse<>(true, message, data, statusCode);
    }

    public static <T> ApiResponse<T> failure(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, statusCode);
    }
}