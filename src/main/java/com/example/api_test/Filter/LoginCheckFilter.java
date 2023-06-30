package com.example.api_test.Filter;

import com.example.api_test.dao.MemberRepository;
import com.example.api_test.domain.MemberDto;
import com.example.api_test.domain.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginCheckFilter implements Filter {

    private final MemberRepository memberRepository;
//    Map<Integer, MemberEntity> memberMap = new HashMap<>();
    private static final String[] whitelist = { "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
//        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                String cmnString = httpRequest.getHeader("cmn");
                if (isValid(cmnString)) {
                    int cmn = Integer.parseInt(cmnString);
                    MemberEntity memberEntity = findByCmn(cmn);
                    log.info("memberEntity setting : {}", memberEntity);
                    httpRequest.setAttribute("memberEntity", memberEntity);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    private boolean isLoginCheckPath(String requestURI) {
        return Arrays.stream(whitelist)
                .noneMatch(requestURI::contains);    }

    private MemberEntity findByCmn(int cmn) {
        return memberRepository.findByCmn(cmn)
                .orElseThrow(() -> new IllegalStateException("Member is not found by DB"));
    }

    private boolean isValid(String cmnString) {
        return !cmnString.equals("") && !cmnString.equals(" ") && cmnString != null;
    }

//    @Cacheable(value = "memberEntity", key = "#cmn")
//    public MemberEntity getCacheMemberDetail(int cmn) {
//        log.info("get MemberInfo from DB");
//        return memberRepository.findByCmn(cmn)
//                .orElseThrow(() -> new IllegalStateException("no cmn"));
//    }
}
