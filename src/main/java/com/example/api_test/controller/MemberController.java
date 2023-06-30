package com.example.api_test.controller;

import com.example.api_test.config.RequestContext;
import com.example.api_test.domain.*;
import com.example.api_test.interceptor.Auth;
import com.example.api_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
@Auth(role = Role.USER)
public class MemberController {
    private final MemberService memberService;

    // list
    @PostMapping(value = "", produces = "application/json")
    @Auth(role = Role.ADMIN)
    public ResponseEntity<Object> insertMember(MemberDto memberDto, String password) {
        log.info("memberDto : {}, password : {}", memberDto, password);
        MemberDto dto = memberService.insertMember(memberDto, password);
        return ResultEntity.SUCCESS.response(dto);
    }

    @GetMapping(value = "/{cmn}", produces = "application/json")
    public ResponseEntity<Object> selectMember(@PathVariable("cmn") int cmn) {
        MemberDto dto = memberService.selectMember(cmn);
        return ResultEntity.SUCCESS.response(dto);
    }

    @PatchMapping(value = "/{cmn}", produces = "application/json")
    public ResponseEntity<Object> updateMember(@PathVariable("cmn") int cmn, MemberDto memberDto, HttpServletRequest httpServletRequest) {
        if (cmn != ((MemberEntity) httpServletRequest.getAttribute("memberEntity")).getCmn()) {
            throw new IllegalStateException("invalidCmn");
        }
        MemberDto dto = memberService.updateMember(memberDto);
        return ResultEntity.SUCCESS.response(dto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteMember(@PathVariable("id") String id, MemberDto memberDto) {
        MemberDto dto = memberService.deleteMember(memberDto);
        return ResultEntity.SUCCESS.response(dto);
    }

    @GetMapping(value = "/{cmn}/hist", produces = "application/json")
    public ResponseEntity<Object> selectMemberHistPage(Pageable pageable, @PathVariable int cmn) {
        PageInfoDto dto = memberService.selectMemberHistPage(cmn, pageable);
        return ResultEntity.SUCCESS.response(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(String id, String password) {
        int cmn = memberService.tryLogin(id, password);
        return ResultEntity.SUCCESS.response(cmn);
    }
}
