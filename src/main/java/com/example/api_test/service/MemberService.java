package com.example.api_test.service;

import com.example.api_test.dao.MemberRepository;
import com.example.api_test.domain.*;
import com.example.api_test.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper = MemberMapper.MAPPER;

    private final MemberRepository memberRepository;


    public MemberDto insertMember(MemberDto memberDto, String password) {
        MemberEntity memberEntity = memberMapper.toEntity(memberDto, password);
        memberRepository.insertMember(memberEntity);
        MemberEntity result = memberRepository.findById(memberEntity.getId()).orElseThrow();
        memberDto = memberMapper.toDto(result);
        return memberDto;
    }

    public MemberDto selectMember(int cmn) {
        MemberEntity memberEntity = memberRepository.findByCmn(cmn)
                .orElseThrow();
        log.info("memberEntity : {}", memberEntity);
        return memberMapper.toDto(memberEntity);
    }

    public MemberDto updateMember(MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findByCmn(memberDto.getCmn())
                .orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        List<MemberModHistoryEntity> memberModHistoryEntities = buildMemberHistory(memberEntity, memberDto, now);
        memberMapper.merge(memberEntity, memberDto);
        memberEntity.setModifyDateTime(now);
        log.info("DB에서 값을 가져옴 - cmn : {}", memberEntity.getCmn());
        memberRepository.updateMember(memberEntity);
        log.info("memberEntity setting : {}", memberEntity);
        // insert -> union mybatis 에서 list 값 insert
        // foreach -> insert
        memberModHistoryEntities.forEach(memberRepository::insertModHist); // memberModHistoryEntities에 있는 값 insert
        MemberEntity result = memberRepository.findById(memberEntity.getId()).orElseThrow();
        return memberMapper.toDto(result);
    }

    public MemberDto deleteMember(MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findById(memberDto.getId()).orElseThrow();
        log.info("memberEntity : {}", memberEntity);
        log.info("DB에서 값을 가져옴 - cmn : {}", memberEntity.getCmn());
        memberRepository.deleteMember(memberEntity);
        log.info("memberEntity setting : {}", memberEntity);
        MemberEntity result = memberRepository.findById(memberEntity.getId()).orElseThrow();
        return memberMapper.toDto(result);
    }

    private List<MemberModHistoryEntity> buildMemberHistory(MemberEntity memberEntity, MemberDto memberDto, LocalDateTime now) {
        List<MemberModHistoryEntity> memberModHistoryEntities = new ArrayList<>();
        int orderNum = memberRepository.selectNextOrderNum(memberEntity.getCmn());
        Field[] fields = memberDto.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(field -> !field.getName().equals("cmn") && !field.getName().equals("modifyDateTime") && !field.getName().equals("registDateTime")).forEach(field -> {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                Object o = field.get(memberDto);
                String afterValue = String.valueOf(o);
                if (o != null && !o.equals("") && !o.equals(" ")) {
                    String beforeValue = validData(fieldName, afterValue, memberEntity);
                    memberModHistoryEntities.add(buildHistoryEntity(fieldName, afterValue, beforeValue, now, memberEntity, orderNum));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return memberModHistoryEntities;
    }

    //     memberEntity 뽑아서 돌리기
    private String validData(String fieldName, String afterValue, MemberEntity memberEntity) {
        String result = null;
        for (Field field : memberEntity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals(fieldName)) {
                try {
                    Object o1 = field.get(memberEntity);
                    String beforeValue = String.valueOf(o1);
                    if (!afterValue.equals(beforeValue)) {
                        result = beforeValue;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    private MemberModHistoryEntity buildHistoryEntity(String fieldName, String afterValue, String beforeValue, LocalDateTime now, MemberEntity memberEntity, int orderNum) {
        MemberModHistoryEntity memberModHistoryEntity = new MemberModHistoryEntity();
        memberModHistoryEntity.setCmn(memberEntity.getCmn());
        memberModHistoryEntity.setTarget(fieldName);
        memberModHistoryEntity.setBeforeChanges(beforeValue);
        memberModHistoryEntity.setAfterChanges(afterValue);
        memberModHistoryEntity.setModifyDateTime(now);
        memberModHistoryEntity.setOrderNum(orderNum);
        log.info("memberModHistoryEntity : {}", memberModHistoryEntity);
        return memberModHistoryEntity;
    }


    public PageInfoDto selectMemberHistPage(int cmn, Pageable pageable) {
        int totalPageNum = memberRepository.modHistoryMaxPaging(cmn);
        List<MemberModHistoryEntity> memberModHistoryEntities = memberRepository.selectModHistoryPaging(pageable.getCurrentPageNum(), cmn);
        List<Object> list = new LinkedList<>();
        Set<Integer> integerSet = memberModHistoryEntities.stream().map(MemberModHistoryEntity::getOrderNum).collect(Collectors.toSet());
        // 방법 1
//        Map<Integer, List<MemberModHistoryEntity>> map = new HashMap<>();
//        integerSet.forEach(num -> {
//            map.put(num, new ArrayList<>());
//        });
//        memberModHistoryEntities.forEach(mem -> {
//            List<MemberModHistoryEntity> targetList = map.get(mem.getOrderNum());
//            targetList.add(mem);
//        });
//        map.keySet().forEach(
//               key -> list.add(map.get(key))
//        );
        // 방법 2
        integerSet.forEach(num -> {
            List<MemberModHistoryEntity> memberModHistoryEntityList = new ArrayList<>();
            memberModHistoryEntities.forEach(MemberModHistoryEntity -> {
                if (MemberModHistoryEntity.getOrderNum() == num) {
                    memberModHistoryEntityList.add(MemberModHistoryEntity);
                }
            });
            list.add(memberModHistoryEntityList);
        });
        return new PageInfoDto(pageable.getCurrentPageNum(), totalPageNum, list);
    }

    public int tryLogin(String id, String pwd) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("not found MEMBER by DB"));
        isValidPassword(memberEntity.getPassword(), pwd);
        return memberEntity.getCmn();
    }

    private void isValidPassword(String password, String pwd) {
        if (!password.equals(pwd)) {
            throw new IllegalStateException("invalidPassword");
        }
    }

}
