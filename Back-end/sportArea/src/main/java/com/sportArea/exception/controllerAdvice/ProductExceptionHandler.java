package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.ProductException;
import com.sportArea.exception.model.ProductExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class ProductExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ProductExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ProductExceptionResponse> handlerException(ProductException exc) {
        ProductExceptionResponse productExceptionResponse = new ProductExceptionResponse( exc.getHttpStatus().value(),
                exc.getMessage(),
                new Timestamp(System.currentTimeMillis()));
//        productExceptionResponse.setStatus(exc.getHttpStatus().value());
//        productExceptionResponse.setMessage(exc.getMessage());
//        productExceptionResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        logger.warn("From ProductExeptionHandler method -handlerException- send message error ({})",
                productExceptionResponse.getMessage());

        return new ResponseEntity<>(productExceptionResponse, exc.getHttpStatus());
    }
}
