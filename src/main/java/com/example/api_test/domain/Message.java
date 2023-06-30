package com.example.api_test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Message {

    // cmn 값
    private int cmn;
    // 메세지 본문
    private String payload;

    public Message(int cmn, String payload) {
        this.cmn = cmn;
        this.payload = payload;
    }
}
