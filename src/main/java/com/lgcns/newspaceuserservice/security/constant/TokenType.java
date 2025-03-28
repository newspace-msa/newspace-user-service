package com.lgcns.newspaceuserservice.security.constant;
import lombok.Getter;

// 리프레시 토큰 ? 액세스 토큰 ?
@Getter
public enum TokenType {
    ACCESS,
    REFRESH;
}