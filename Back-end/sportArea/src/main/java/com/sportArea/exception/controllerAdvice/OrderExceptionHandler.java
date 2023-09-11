package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.OrderException;
import com.sportArea.exception.model.OrderErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class OrderExceptionHandler {

    Logger logger = LoggerFactory.getLogger(OrderExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<OrderErrorResponse> handlerException(OrderException exc) {
        OrderErrorResponse orderErrorResponse = new OrderErrorResponse();
        orderErrorResponse.setStatus(exc.getHttpStatus().value());
        orderErrorResponse.setMessage( exc.getMessage());
        orderErrorResponse.setTimeStamp( new Timestamp(System.currentTimeMillis()));
        logger.warn("From OrderExceptionHandler method -handlerException- send message error ({})",
                orderErrorResponse.getMessage());

        return new ResponseEntity<>(orderErrorResponse, exc.getHttpStatus());
    }
}
