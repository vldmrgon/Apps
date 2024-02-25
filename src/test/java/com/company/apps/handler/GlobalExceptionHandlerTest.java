package com.company.apps.handler;

import com.company.apps.exception.PlayerBusinessException;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void handleGeneralExceptionTest() {
        Exception exception = new Exception("General error");
        ResponseEntity<String> response = exceptionHandler.handleGeneralException(exception);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Internal server error", response.getBody());
    }

    @Test
    void handleBusinessExceptionTest() {
        PlayerBusinessException exception = new PlayerBusinessException("Business error");
        ResponseEntity<String> response = exceptionHandler.handleBusinessException(exception);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Business error", response.getBody());
    }
}