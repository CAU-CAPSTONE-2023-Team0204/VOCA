package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private Role role;
}
