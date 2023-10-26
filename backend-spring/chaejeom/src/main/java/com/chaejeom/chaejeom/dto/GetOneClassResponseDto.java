package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetOneClassResponseDto {
    private Long id;

    private String name;

    private List<Member> memberList;

}
