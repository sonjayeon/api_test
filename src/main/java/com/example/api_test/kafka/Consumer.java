package com.example.api_test.kafka;

import com.example.api_test.dao.MessageRepository;
import com.example.api_test.domain.Message;
import com.example.api_test.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Consumer {
    private final MessageService messageService;

    // DB 저장
    @KafkaListener(topics = "chat", groupId = "group-02")
    public void receiveMessage(Message message) {
        log.info("message : {}", message);
        messageService.saveMessage(message);
    }
}
