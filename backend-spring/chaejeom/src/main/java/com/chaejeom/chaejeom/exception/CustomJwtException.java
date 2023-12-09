package com.chaejeom.chaejeom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomJwtException extends RuntimeException{
    ErrorCode errorCode;
}
