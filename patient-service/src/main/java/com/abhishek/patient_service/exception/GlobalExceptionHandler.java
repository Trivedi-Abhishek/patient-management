package com.abhishek.patient_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errorMap=new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error->errorMap.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorMap);

    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotFoundException(EmailNotFoundException ex){

        log.warn("Email already exists {}",ex.getMessage());
        Map<String, String> errorMap=new HashMap<>();
        errorMap.put("message", "Email address already exists");
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex){

        log.warn("Patient not found {}", ex.getMessage());
        Map<String, String> errorMap=new HashMap<>();
        errorMap.put("message", "Patient not found");
        return ResponseEntity.badRequest().body(errorMap);
    }
}
