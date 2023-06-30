package com.example.api_test.dao;

import com.example.api_test.domain.MemberEntity;
import com.example.api_test.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MessageRepository {
    void save(Message message);
}
