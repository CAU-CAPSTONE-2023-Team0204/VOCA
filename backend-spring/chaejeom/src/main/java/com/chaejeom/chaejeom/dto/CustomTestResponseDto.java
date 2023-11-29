package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomTestResponseDto {
    private String name;
    private String username;
    private LocalDate date;
    private List<TestContent> testContentList;

    public static CustomTestResponseDto of(Test test, Member member){
        return new CustomTestResponseDto(test.getName(), member.getUsername(), test.getDate(), test.getTestContentList());
    }
}
