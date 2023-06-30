package com.example.api_test.kafka;

import com.example.api_test.domain.Message;
import com.example.api_test.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {
    private static final String TOPIC = "chat";
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(Message message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}
