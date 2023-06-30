package com.example.api_test.mapper;

import com.example.api_test.domain.MemberDto;
import com.example.api_test.domain.MemberEntity;
import com.example.api_test.domain.MemberModHistoryDto;
import com.example.api_test.domain.MemberModHistoryEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-22T17:06:54+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberEntity toEntity(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setCmn( memberDto.getCmn() );
        memberEntity.setId( memberDto.getId() );
        memberEntity.setName( memberDto.getName() );
        memberEntity.setRegistDateTime( memberDto.getRegistDateTime() );
        memberEntity.setModifyDateTime( memberDto.getModifyDateTime() );
        memberEntity.setGender( memberDto.getGender() );
        memberEntity.setDating( memberDto.getDating() );
        memberEntity.setIntroduce( memberDto.getIntroduce() );

        return memberEntity;
    }

    @Override
    public MemberEntity toEntity(MemberDto memberDto, String password) {
        if ( memberDto == null && password == null ) {
            return null;
        }

        MemberEntity memberEntity = new MemberEntity();

        if ( memberDto != null ) {
            memberEntity.setCmn( memberDto.getCmn() );
            memberEntity.setId( memberDto.getId() );
            memberEntity.setName( memberDto.getName() );
            memberEntity.setRegistDateTime( memberDto.getRegistDateTime() );
            memberEntity.setModifyDateTime( memberDto.getModifyDateTime() );
            memberEntity.setGender( memberDto.getGender() );
            memberEntity.setDating( memberDto.getDating() );
            memberEntity.setIntroduce( memberDto.getIntroduce() );
        }
        if ( password != null ) {
            memberEntity.setPassword( password );
        }

        return memberEntity;
    }

    @Override
    public MemberModHistoryEntity toEntity(MemberModHistoryDto memberModHistoryDto) {
        if ( memberModHistoryDto == null ) {
            return null;
        }

        MemberModHistoryEntity memberModHistoryEntity = new MemberModHistoryEntity();

        memberModHistoryEntity.setCmn( memberModHistoryDto.getCmn() );
        memberModHistoryEntity.setModifyDateTime( memberModHistoryDto.getModifyDateTime() );
        memberModHistoryEntity.setBeforeChanges( memberModHistoryDto.getBeforeChanges() );
        memberModHistoryEntity.setAfterChanges( memberModHistoryDto.getAfterChanges() );
        memberModHistoryEntity.setTarget( memberModHistoryDto.getTarget() );
        memberModHistoryEntity.setOrderNum( memberModHistoryDto.getOrderNum() );

        return memberModHistoryEntity;
    }

    @Override
    public MemberDto toDto(MemberEntity memberEntity) {
        if ( memberEntity == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setCmn( memberEntity.getCmn() );
        memberDto.setId( memberEntity.getId() );
        memberDto.setName( memberEntity.getName() );
        memberDto.setRegistDateTime( memberEntity.getRegistDateTime() );
        memberDto.setModifyDateTime( memberEntity.getModifyDateTime() );
        memberDto.setGender( memberEntity.getGender() );
        memberDto.setDating( memberEntity.getDating() );
        memberDto.setIntroduce( memberEntity.getIntroduce() );

        return memberDto;
    }

    @Override
    public MemberModHistoryDto toDto(MemberModHistoryEntity memberModHistoryEntity) {
        if ( memberModHistoryEntity == null ) {
            return null;
        }

        MemberModHistoryDto memberModHistoryDto = new MemberModHistoryDto();

        memberModHistoryDto.setCmn( memberModHistoryEntity.getCmn() );
        memberModHistoryDto.setModifyDateTime( memberModHistoryEntity.getModifyDateTime() );
        memberModHistoryDto.setBeforeChanges( memberModHistoryEntity.getBeforeChanges() );
        memberModHistoryDto.setAfterChanges( memberModHistoryEntity.getAfterChanges() );
        memberModHistoryDto.setTarget( memberModHistoryEntity.getTarget() );
        memberModHistoryDto.setOrderNum( memberModHistoryEntity.getOrderNum() );

        return memberModHistoryDto;
    }

    @Override
    public void merge(MemberEntity entity, MemberDto dto) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setGender( dto.getGender() );
        entity.setDating( dto.getDating() );
        entity.setIntroduce( dto.getIntroduce() );
    }
}
