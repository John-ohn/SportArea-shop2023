package com.sportArea.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class CommentErrorResponse extends AbstractErrorResponse{

    public CommentErrorResponse(int status, String message, Timestamp timeStamp){
        super(status, message, timeStamp);
    }
}
