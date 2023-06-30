package com.example.api_test.service;

import com.example.api_test.domain.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@SpringBootTest
public class ChatServiceTest {
    private final MessageSendService messageSendService;
    private Message message;

    public ChatServiceTest(MessageSendService messageSendService){
        this.messageSendService = messageSendService;
    }

    @BeforeEach
    void setUp() {
        message = new Message(12, "asdf");
    }

    @Test
    void sendMessage() {
        messageSendService.sendMessage(message);
    }

}
