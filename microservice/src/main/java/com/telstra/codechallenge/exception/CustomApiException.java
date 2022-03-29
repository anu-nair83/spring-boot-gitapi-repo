package com.telstra.codechallenge.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class CustomApiException {
    private HttpStatus status;
    private String timestamp;
    private String message;

    private CustomApiException() {
        timestamp = LocalDateTime.now().toString();
    }
 
    CustomApiException(HttpStatus status) {
        this();
        this.status = status;
    }
 
    CustomApiException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }
 
    CustomApiException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }
 }