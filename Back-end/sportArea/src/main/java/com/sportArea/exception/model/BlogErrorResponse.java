package com.sportArea.exception.model;

import com.sportArea.exception.AbstractGeneralException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class BlogErrorResponse extends AbstractErrorResponse {

    public BlogErrorResponse(int status, String message, Timestamp timeStamp){
        super(status, message, timeStamp);
    }
}
