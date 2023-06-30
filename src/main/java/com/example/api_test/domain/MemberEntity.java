package com.example.api_test.domain;

import com.example.api_test.interceptor.Auth;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MemberEntity {
    private int cmn;
    private String id;
    private String name;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDateTime;
    private Gender gender;
    private Dating dating;
    private String introduce;
    private String role;
}