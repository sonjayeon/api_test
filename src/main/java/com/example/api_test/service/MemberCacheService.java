//package com.example.api_test.service;
//
//import com.example.api_test.dao.MemberRepository;
//import com.example.api_test.domain.MemberEntity;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MemberCacheService {
//    private final MemberRepository memberRepository;
//
//    @Cacheable(value = "memberEntity", key = "#cmn")
//    public MemberEntity getCacheMemberDetail(int cmn) {
//        log.info("get MemberInfo from DB");
//        return memberRepository.findByCmn(cmn)
//                .orElseThrow(() -> new IllegalStateException("no cmn"));
//    }
//
//    @CacheEvict(value = "userEntity", key = "#cmn")
//    public void dropCacheUserDetail(int cmn){}
//}
