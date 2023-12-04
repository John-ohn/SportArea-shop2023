package com.sportArea.entity.dto.logger;

import com.sportArea.controller.ProductUAController;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class GeneralLogg {

    private final Logger logger ;

    public GeneralLogg (){

        this.logger = LoggerFactory.getLogger(GeneralLogg.class);
    }

    public void getLoggerControllerInfo(String nameClass , String nameMethod, String methodUrl, String message ){

        logger.info("From  {}, method - {} -  {}. Return {}",
                nameClass ,
                nameMethod,
                methodUrl,
                message);
    }

    public void getLoggerControllerWarn(String nameClass , String nameMethod, String methodUrl, String message,  HttpStatus httpStatus ){

        logger.warn("From {}, method - {} -  {}. send war message (  {}   status code ({}))",
                nameClass ,
                nameMethod,
                methodUrl,
                message,
                httpStatus.toString());
    }
}
