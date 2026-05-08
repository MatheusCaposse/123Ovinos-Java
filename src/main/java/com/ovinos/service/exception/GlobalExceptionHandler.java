package com.ovinos.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SheepException.class)
    public ResponseEntity<Map<String, String>> sheepException(SheepException e) {

        Map<String, String> error = new HashMap<>();

        error.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(error);
    }
}
