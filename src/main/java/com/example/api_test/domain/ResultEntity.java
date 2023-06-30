package com.example.api_test.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public enum ResultEntity {
    UNKNOWN_REASON ("CODE000","정의되지 않은 오류입니다."),
    UNKNOWN_CMN ("CODE001","UnknownCmn"),
    INVALID_ID ("CODE002","InvalidId"),
    INVALID_PASSWORD ("CODE003","InvalidPassword"),
    SUCCESS("CODE004","LoginSuccess");

    private final String resultCode;
    private final String resultMessage;

    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("resultCode",this.resultCode);
        headers.add("resultMessage",this.resultMessage);
        return headers;
    }

    public ResponseEntity<Object> response(){
        return ResponseEntity
                .ok()
                .headers(getHeaders())
                .build();
    }

    public ResponseEntity<Object> response(Object result) {
        return ResponseEntity
                .ok()
                .headers(getHeaders())
                .body(result);
    }

}
