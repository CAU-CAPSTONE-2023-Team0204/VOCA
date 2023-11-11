package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManualTestResponseDto {
    private String name;
    private String className;
    private LocalDateTime time;
    private List<TestContent> testContentList;

    public static ManualTestResponseDto of(Test test){
        return new ManualTestResponseDto(test.getName(), test.getUserClass().getName(), test.getTime(), test.getTestContentList());
    }
}
