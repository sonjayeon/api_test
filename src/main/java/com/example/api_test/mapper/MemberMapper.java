package com.example.api_test.mapper;

import com.example.api_test.domain.MemberDto;
import com.example.api_test.domain.MemberEntity;
import com.example.api_test.domain.MemberModHistoryDto;
import com.example.api_test.domain.MemberModHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper
public interface MemberMapper {
    MemberMapper MAPPER = Mappers.getMapper(MemberMapper.class);

    MemberEntity toEntity(MemberDto memberDto);

    MemberEntity toEntity(MemberDto memberDto, String password);
    MemberModHistoryEntity toEntity(MemberModHistoryDto memberModHistoryDto);

    MemberDto toDto(MemberEntity memberEntity);
    MemberModHistoryDto toDto(MemberModHistoryEntity memberModHistoryEntity);
    @Mapping(target = "cmn", ignore = true)
    @Mapping(target = "registDateTime", ignore = true)
    @Mapping(target = "modifyDateTime", ignore = true)
    void merge(@MappingTarget MemberEntity entity, MemberDto dto);

}
