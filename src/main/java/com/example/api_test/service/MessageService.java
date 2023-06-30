package com.example.api_test.service;

import com.example.api_test.dao.MemberRepository;
import com.example.api_test.dao.MessageRepository;
import com.example.api_test.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
