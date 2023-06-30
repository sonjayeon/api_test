package com.example.api_test.exception;

import com.example.api_test.domain.ResultEntity;
import com.example.api_test.exception.handler.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RestControllerExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Object> handleResponseException(ResponseException responseException) {
        log.error(responseException.toString());
        return responseException.getErrorResponse().response();
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Object> handleResponseException(Exception e){
        e.printStackTrace();
        return ResultEntity.UNKNOWN_REASON.response();
    }
}
