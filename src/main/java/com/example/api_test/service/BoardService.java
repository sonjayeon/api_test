package com.example.api_test.service;

import com.example.api_test.dao.BoardRepository;
import com.example.api_test.domain.BoardDto;
import com.example.api_test.domain.BoardEntity;
import com.example.api_test.domain.BoardListDto;
import com.example.api_test.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper = BoardMapper.MAPPER;

    private final BoardRepository boardRepository;


    public List<BoardEntity> selectBoard() {
        List<BoardEntity> boardEntityList = boardRepository.getBoard();
         boardMapper.toDto((BoardEntity) boardEntityList);
        return boardEntityList;
    }

    public BoardDto insertBoard(int cmn, String title, String payload) {
        return null;
    }

    public BoardDto detailBoard(int seq) {
        return null;
    }
}
