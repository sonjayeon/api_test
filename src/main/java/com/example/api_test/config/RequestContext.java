package com.example.api_test.config;

import com.example.api_test.domain.MemberEntity;

import javax.servlet.http.HttpServletRequest;

public class RequestContext {

    public static MemberEntity getMemberEntity(HttpServletRequest request){
        return (MemberEntity) request.getAttribute("memberEntity");
    }
}
