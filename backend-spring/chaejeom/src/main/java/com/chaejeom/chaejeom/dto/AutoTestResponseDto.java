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
public class AutoTestResponseDto {
    private String name;
    private String className;
    private String vocabListName;
    private LocalDateTime time;
    private List<TestContent> testContentList;

    public static AutoTestResponseDto of(Test test){
        return new AutoTestResponseDto(test.getName(), test.getUserClass().getName(), test.getVocabList().getName(), test.getTime(), test.getTestContentList());
    }

}
