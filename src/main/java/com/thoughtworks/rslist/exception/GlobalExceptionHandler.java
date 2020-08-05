package com.thoughtworks.rslist.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@Slf4j
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidIndexException.class, InvalidParamException.class, MethodArgumentNotValidException.class})
    public ResponseEntity exceptionHandler(Exception e) {

        String errorMessage;
        CommonError commonError = new CommonError();
        Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        if (e instanceof MethodArgumentNotValidException) {
            errorMessage = "invalid param";
        } else {
            errorMessage = e.getMessage();
        }

        commonError.setError(errorMessage);
        logger.error("ERROR IS {} : {}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonError);
    }
}
