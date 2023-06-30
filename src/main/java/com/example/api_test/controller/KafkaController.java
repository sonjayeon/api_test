package com.example.api_test.controller;

import com.example.api_test.domain.MemberDto;
import com.example.api_test.domain.MemberEntity;
import com.example.api_test.domain.Message;
import com.example.api_test.domain.ResultEntity;
import com.example.api_test.kafka.Producer;
import com.example.api_test.service.MessageSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final MessageSendService messageSendService;

    @PostMapping("/text")
    public ResponseEntity<Object> sendMessage(String payload, HttpServletRequest request) {
        MemberEntity memberEntity = (MemberEntity) request.getAttribute("memberEntity");
        int cmn = memberEntity.getCmn();
        Message message = new Message(cmn, payload);
        messageSendService.sendMessage(message);
        return ResultEntity.SUCCESS.response();
    }
}
