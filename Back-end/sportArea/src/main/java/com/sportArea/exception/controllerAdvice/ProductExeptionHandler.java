package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.ProductExeption;
import com.sportArea.exception.model.ProductExeptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExeptionHandler {

    Logger logger = LoggerFactory.getLogger(ProductExeptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ProductExeptionResponse> handlerException(ProductExeption exc) {
        ProductExeptionResponse productExeptionResponse = new ProductExeptionResponse();
        productExeptionResponse.setStatus(exc.getHttpStatus().value());
        productExeptionResponse.setMessage(exc.getMessage());
        productExeptionResponse.setTimeStamp(System.currentTimeMillis());
        logger.warn("From ProductExeptionHandler method -handlerException- send message error ({})",
                productExeptionResponse.getMessage());

        return new ResponseEntity<>(productExeptionResponse, exc.getHttpStatus());
    }
}
