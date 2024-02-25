package com.company.apps.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.company.apps.exception.PlayerBusinessException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        log.error("Internal server error: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    @ExceptionHandler({PlayerBusinessException.class})
    public ResponseEntity<String> handleBusinessException(RuntimeException e) {
        log.error("Business exception encountered: ", e);
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> handleException(Throwable e, HttpStatus status) {
        log.error("Handling exception: Status - {}, Message - {}", status, e.getMessage(), e);
        return ResponseEntity.status(status).body(e.getMessage());
    }

    private ResponseEntity<String> handleException(String message) {
        log.error("Handling exception with custom message: {}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}