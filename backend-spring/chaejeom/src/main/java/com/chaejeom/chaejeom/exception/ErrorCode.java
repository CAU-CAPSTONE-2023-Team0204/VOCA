package com.chaejeom.chaejeom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "TOKEN-001","잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN-002", "만료된 JWT 토큰입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "TOKEN-003", "지원되지 않는 JWT 토큰입니다."),
    TOKEN_ILLEGAL(HttpStatus.UNAUTHORIZED, "TOKEN-005", "JWT 토큰이 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
