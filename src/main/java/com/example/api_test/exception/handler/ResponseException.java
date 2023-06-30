package com.example.api_test.exception.handler;

import com.example.api_test.domain.ResultEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class ResponseException extends RuntimeException {
    ResultEntity errorResponse;
    private String originalExceptionMessage;

    public ResponseException(ResultEntity errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ResponseException(Exception exception) {
        this.errorResponse = ResultEntity.UNKNOWN_REASON;
        this.originalExceptionMessage = exception.getMessage();
    }

    public ResponseException(ResultEntity errorResponse, Exception exception) {
        this.errorResponse = errorResponse;
        this.originalExceptionMessage = exception.getMessage();
    }

    @Override
    public String toString() {
        return "ResponseException{" +
                "errorResponse=" + errorResponse +
                ", originalExceptionMessage='" + originalExceptionMessage + '\'' +
                '}';
    }
}
