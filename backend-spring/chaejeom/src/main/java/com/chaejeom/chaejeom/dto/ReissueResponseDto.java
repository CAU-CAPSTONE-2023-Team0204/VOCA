package com.chaejeom.chaejeom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReissueResponseDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
}
