package com.example.api_test.dao;

import com.example.api_test.domain.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardRepository {

    List<BoardEntity> getBoard();
    Optional<BoardEntity> findBySeq(int seq);
    int insertBoard(BoardEntity boardEntity);
}
