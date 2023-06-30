package com.example.api_test.mapper;

import com.example.api_test.domain.BoardDto;
import com.example.api_test.domain.BoardEntity;
import com.example.api_test.domain.BoardListDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BoardMapper {

    BoardMapper MAPPER = Mappers.getMapper(BoardMapper.class);

    BoardEntity toEntity(BoardDto boardDto);
    BoardEntity toEntity(BoardListDto boardListDto);

    BoardDto toDto(BoardEntity boardEntity);


}
