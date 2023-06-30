package com.example.api_test.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    private int seq;
    private String userId;
    private int cmn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDateTime;
    private String title;
    private String payload;

    public BoardDto(int cmn, LocalDateTime registDateTime, LocalDateTime modifyDateTime, String title, String payload) {
        this.cmn = cmn;
        this.registDateTime = registDateTime;
        this.modifyDateTime = modifyDateTime;
        this.title = title;
        this.payload = payload;
    }
}
