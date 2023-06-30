package com.example.api_test.service;

import com.example.api_test.dao.BoardRepository;
import com.example.api_test.domain.BoardDto;
import com.example.api_test.domain.BoardEntity;
import com.example.api_test.domain.BoardListDto;
import com.example.api_test.mapper.BoardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BoardServiceTest {
    static Logger log = LoggerFactory.getLogger(BoardServiceTest.class);
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    private BoardMapper boardMapper = BoardMapper.MAPPER;
    private BoardDto boardDto;
    private BoardListDto boardListDto;


    void setUp() {
        boardDto = new BoardDto(1, LocalDateTime.now(), LocalDateTime.now(), "title", "payload");
    }

    @Test
    public void BoardTest() {
        setUp();
        BoardEntity boardEntity = boardMapper.toEntity(boardDto);
        log.info("boardEntity : {}", boardEntity.toString());
        int insertResult = boardRepository.insertBoard(boardEntity);
        log.info("insertResult : {}", insertResult);
    }
}
