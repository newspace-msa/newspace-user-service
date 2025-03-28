package com.lgcns.newspaceuserservice.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.newspaceuserservice.exception.UserResponseStatus;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FilterResponseUtil {

    public static void sendFilterResponse(HttpServletResponse httpServletResponse,
                                          int statusCode,
                                          UserResponseStatus baseResponseStatus) throws IOException {

        // 응답 상태 및 헤더 설정
        httpServletResponse.setStatus(statusCode);
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        // 응답 본문 작성
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(baseResponseStatus));
    }
}
