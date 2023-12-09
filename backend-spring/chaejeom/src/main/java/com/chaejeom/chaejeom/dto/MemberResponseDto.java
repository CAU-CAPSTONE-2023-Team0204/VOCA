package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String username;
    private String email;
    private String name;
    private Role role;
    public static MemberResponseDto of(Member member) {

        return new MemberResponseDto(member.getUsername(), member.getEmail(), member.getName(), member.getRole());
    }
}