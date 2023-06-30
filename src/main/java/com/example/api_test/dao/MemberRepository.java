package com.example.api_test.dao;

import com.example.api_test.domain.MemberEntity;
import com.example.api_test.domain.MemberModHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {
    void insertMember(MemberEntity memberEntity);

    Optional<MemberEntity> findById(String id);

    void updateMember(MemberEntity memberEntity);

    Optional<MemberEntity> findByCmn(int cmn);

    void deleteMember(MemberEntity memberEntity);

    void insertModHist(MemberModHistoryEntity memberModHistoryEntity);

    MemberModHistoryEntity selectMemberModHist(int cmn);

    int selectNextOrderNum(int cmn);

    List<MemberModHistoryEntity> modHistoryPaging(int cmn);

    int modHistoryMaxPaging(int cmn);

    List<MemberModHistoryEntity> selectModHistoryPaging(@Param("currentPageNum") int currentPageNum, @Param("cmn") int cmn);
}
