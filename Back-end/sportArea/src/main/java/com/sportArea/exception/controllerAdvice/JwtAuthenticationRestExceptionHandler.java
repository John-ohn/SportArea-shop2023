package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.JwtAuthenticationException;
import com.sportArea.exception.model.JwtAuthenticationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtAuthenticationRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<JwtAuthenticationErrorResponse> handlerException(JwtAuthenticationException exc) {
        JwtAuthenticationErrorResponse jwtAuthenticationError = new JwtAuthenticationErrorResponse();
        jwtAuthenticationError.setStatus(exc.getHttpStatus().value());
        jwtAuthenticationError.setMessage(exc.getMessage());
        jwtAuthenticationError.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(jwtAuthenticationError, exc.getHttpStatus());
    }
}
