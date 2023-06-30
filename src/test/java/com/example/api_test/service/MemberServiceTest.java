package com.example.api_test.service;

import com.example.api_test.dao.MemberRepository;
import com.example.api_test.domain.*;
import com.example.api_test.mapper.MemberMapper;
import com.sun.tools.jconsole.JConsoleContext;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberServiceTest {

    static Logger log = LoggerFactory.getLogger(MemberServiceTest.class);

    private MemberEntity memberEntity = new MemberEntity();
    private MemberModHistoryEntity memberModHistoryEntity = new MemberModHistoryEntity();
    private MemberDto memberDto = new MemberDto();
    private MemberModHistoryDto memberModHistoryDto = new MemberModHistoryDto();
    private Pageable pageable = new Pageable();
    @Autowired
    private MemberRepository memberRepository;
    private int cmn = 9;

    private final MemberMapper memberMapper = MemberMapper.MAPPER;

    private String password;

    @BeforeEach
    void setUp() {
        memberDto.setCmn(9);
        memberDto.setId("id");
        memberDto.setName("");
        memberDto.setModifyDateTime(LocalDateTime.now());
        memberDto.setGender(Gender.WOMAN);
        memberDto.setDating(Dating.N);
        memberDto.setIntroduce(" ");

        memberEntity.setCmn(9);
        memberEntity.setId("id2222");
        memberEntity.setName("name");
        memberEntity.setRegistDateTime(LocalDateTime.now()); // insert할 때만
        memberEntity.setModifyDateTime(LocalDateTime.now());
        memberEntity.setGender(Gender.WOMAN);
        memberEntity.setDating(Dating.N);
        memberEntity.setIntroduce("introduce");
        password = "password";
    }

    void setUpV2() {
        memberModHistoryDto.setCmn(9);
//        memberModHistoryDto.setModifyDateTime(LocalDateTime.now());
//        memberModHistoryDto.setBeforeChanges("password");
//        memberModHistoryDto.setAfterChanges("aaa");
//        memberModHistoryDto.setTarget("password");
//        memberModHistoryDto.setOrderNum(2);
        pageable.setCurrentPageNum(1);
    }


    @Test
    public void MemberTest() {
        setUp();
        memberEntity = memberMapper.toEntity(memberDto, password);
        log.info("memberEntity : {}", memberEntity);
        memberRepository.insertMember(memberEntity);
        MemberEntity result = memberRepository.findById(memberEntity.getId()).orElseThrow();
        memberDto = memberMapper.toDto(result);
        log.info("memberDto : {}", memberDto);
    }

//    @Test
//    public void MemberModHistTest() {
//        setUpV2();
//        memberModHistoryEntity = memberMapper.toEntity(memberModHistoryDto);
//        log.info("memberModHistoryEntity : {}", memberModHistoryEntity);
//        memberRepository.insertModHist(memberModHistoryEntity);
//        MemberModHistoryEntity result = memberRepository.selectMemberModHist(memberModHistoryEntity.getCmn());
//        memberModHistoryDto = memberMapper.toDto(result);
//        log.info("memberModHistoryDto : {}", memberModHistoryDto);
//    }

    @Test
    public void MemberModHistTest() {
        /*
        1. memberDto + memberEntity 조합
        2. as-is -> to-be , entity logging
        */
        setUp();
        memberEntity = memberRepository.findByCmn(9)
                .orElseThrow();
        log.info("memberEntity : {}", memberEntity);
        log.info("memberDto : {}", memberDto);
        memberMapper.merge(memberEntity, memberDto);
        log.info("memberEntity : {}, memberDto : {}", memberEntity, memberDto);
        assertEquals(memberDto.getId(), memberEntity.getId());
        assertEquals(memberDto.getName(), memberEntity.getName());
        assertEquals(memberDto.getModifyDateTime(), memberEntity.getModifyDateTime());
        assertEquals(memberDto.getGender(), memberEntity.getGender());
        assertEquals(memberDto.getDating(), memberEntity.getDating());
        assertEquals(memberDto.getIntroduce(), memberEntity.getIntroduce());

//        memberRepository.insertModHist(memberModHistoryEntity);
//        MemberModHistoryEntity result = memberRepository.selectMemberModHist(memberModHistoryEntity.getCmn());
//        log.info("result : {}", result);
//        memberModHistoryDto = memberMapper.toDto(result);
//        log.info("memberModHistoryDto : {}", memberModHistoryDto);
    }

    @Test
    public void getFieldInfo() {
        setUp();
        LocalDateTime now = LocalDateTime.now();
//        buildMemberHistory(now);
        memberMapper.merge(memberEntity, memberDto);
        memberEntity.setModifyDateTime(now);
        memberRepository.updateMember(memberEntity);
        memberRepository.insertModHist(memberModHistoryEntity);
    }

    @Test
    public void setHistoryTestData() {
        setUp();
        IntStream.range(0, 100).forEach(num -> {
            LocalDateTime now = LocalDateTime.now();
            String numString = String.valueOf(num);
            memberDto.setName(numString);
            memberDto.setIntroduce(numString);
            memberDto.setGender(num % 2 == 0 ? Gender.MAN : Gender.WOMAN);
            List<MemberModHistoryEntity> memberModHistoryEntities = buildMemberHistory(memberEntity, memberDto, now);
            memberMapper.merge(memberEntity, memberDto);
            memberEntity.setModifyDateTime(now);
            memberRepository.updateMember(memberEntity);
            memberModHistoryEntities.forEach(memberRepository::insertModHist);
        });
    }

    private List<MemberModHistoryEntity> buildMemberHistory(MemberEntity memberEntity, MemberDto memberDto, LocalDateTime now) {
        List<MemberModHistoryEntity> memberModHistoryEntities = new ArrayList<>();
        int orderNum = memberRepository.selectNextOrderNum(memberEntity.getCmn());
        Field[] fields = memberDto.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> !field.getName().equals("cmn") && !field.getName().equals("modifyDateTime") && !field.getName().equals("registDateTime"))
                .forEach(field -> {
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

    @Test
    public void selectMemberHistPage() {
        setUpV2();
        int totalPageNum = memberRepository.modHistoryMaxPaging(cmn);
        List<MemberModHistoryEntity> memberModHistoryEntities = memberRepository.selectModHistoryPaging(pageable.getCurrentPageNum(), cmn);
        List<Object> list = new LinkedList<>();
        Set<Integer> integerSet = memberModHistoryEntities.stream()
                .map(MemberModHistoryEntity::getOrderNum)
                .collect(Collectors.toSet());
        log.info("integerSet : {}", integerSet);
        integerSet.forEach(num -> {
            log.info("num : {}", num);
            List<MemberModHistoryEntity> memberModHistoryEntityList = new ArrayList<>();
            memberModHistoryEntities.forEach(MemberModHistoryEntity -> {
                if (MemberModHistoryEntity.getOrderNum() == num){
                    memberModHistoryEntityList.add(MemberModHistoryEntity);
                }
            });
            list.add(memberModHistoryEntityList);
        });
        PageInfoDto pageInfoDto = new PageInfoDto(pageable.getCurrentPageNum(), totalPageNum, list);
        log.info("list : {}", pageInfoDto);
    }


}