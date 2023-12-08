package com.sportArea.entity.dto.logger;

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

    public void getLoggerControllerInfoDelete(String nameClass , String nameMethod, String methodUrl, String message ){

        logger.info("From  {}, method - {} -  {}. Delete {}",
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
    public void getLoggerServiceInfo(String nameClass , String nameMethod,  String message ){

        logger.info("From {}, method - {} - . Return {}",
                nameClass ,
                nameMethod,
                message);
    }

    public void getLoggerServiceWarn(String nameClass , String nameMethod, String message,  HttpStatus httpStatus ){

        logger.warn("From {}, method - {} - . send war message (  {}   status code ({}))",
                nameClass ,
                nameMethod,
                message,
                httpStatus.toString());
    }


}
