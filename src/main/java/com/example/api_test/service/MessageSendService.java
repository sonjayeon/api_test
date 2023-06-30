package com.example.api_test.service;

import com.example.api_test.domain.Message;
import com.example.api_test.kafka.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSendService {

    private final Producer producer;

    public void sendMessage(Message message) {
        producer.sendMessage(message);
    }
}
