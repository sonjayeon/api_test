package com.example.api_test.interceptor;

import com.example.api_test.domain.MemberEntity;
import com.example.api_test.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MemberEntity memberEntity = (MemberEntity) request.getAttribute("memberEntity");
        log.info("memberEntity : {}", memberEntity);
        Role role = Role.valueOf(memberEntity.getRole());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth classAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
        Auth methodAuth = handlerMethod.getMethodAnnotation(Auth.class);
        Auth auth = null;

        if (classAuth != null) {
            auth = classAuth;
        }

        if (methodAuth != null) {
            auth = methodAuth;
        }

        // 권한이 필요없는 요청
        if (auth == null) {
            log.info("권한이 필요없는 요청");
            // 통과
            return true;
        }
        log.info("권한이 필요한 요청 - Role : {}", auth.role());

        boolean isAuth = false;
        if (role != null) {
            if (auth.role() == role) {
                log.info("권한 있음. - 요청 Role : {}, 회원 Role : {}", auth.role(), role);
                isAuth = true;
            }
        }

        log.info("권한 여부 - {}", isAuth);
        if (!isAuth) {
            new IllegalStateException("error");
        }
        return isAuth;
    }
}
