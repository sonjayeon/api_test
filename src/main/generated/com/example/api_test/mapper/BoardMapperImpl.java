package com.example.api_test.mapper;

import com.example.api_test.domain.BoardDto;
import com.example.api_test.domain.BoardEntity;
import com.example.api_test.domain.BoardListDto;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-23T16:32:57+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
public class BoardMapperImpl implements BoardMapper {

    @Override
    public BoardEntity toEntity(BoardDto boardDto) {
        if ( boardDto == null ) {
            return null;
        }

        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setSeq( boardDto.getSeq() );
        boardEntity.setUserId( boardDto.getUserId() );
        boardEntity.setCmn( boardDto.getCmn() );
        boardEntity.setRegistDateTime( boardDto.getRegistDateTime() );
        boardEntity.setModifyDateTime( boardDto.getModifyDateTime() );
        boardEntity.setTitle( boardDto.getTitle() );
        boardEntity.setPayload( boardDto.getPayload() );

        return boardEntity;
    }

    @Override
    public BoardEntity toEntity(BoardListDto boardListDto) {
        if ( boardListDto == null ) {
            return null;
        }

        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setSeq( boardListDto.getSeq() );
        boardEntity.setUserId( boardListDto.getUserId() );
        boardEntity.setCmn( boardListDto.getCmn() );
        boardEntity.setRegistDateTime( boardListDto.getRegistDateTime() );
        boardEntity.setModifyDateTime( boardListDto.getModifyDateTime() );
        boardEntity.setTitle( boardListDto.getTitle() );

        return boardEntity;
    }

    @Override
    public BoardDto toDto(BoardEntity boardEntity) {
        if ( boardEntity == null ) {
            return null;
        }

        int cmn = 0;
        LocalDateTime registDateTime = null;
        LocalDateTime modifyDateTime = null;
        String title = null;
        String payload = null;

        cmn = boardEntity.getCmn();
        registDateTime = boardEntity.getRegistDateTime();
        modifyDateTime = boardEntity.getModifyDateTime();
        title = boardEntity.getTitle();
        payload = boardEntity.getPayload();

        BoardDto boardDto = new BoardDto( cmn, registDateTime, modifyDateTime, title, payload );

        boardDto.setSeq( boardEntity.getSeq() );
        boardDto.setUserId( boardEntity.getUserId() );

        return boardDto;
    }
}
