package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestContent;
import com.chaejeom.chaejeom.domain.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponseDto {
    private Long testId;
    private String name;
    private String className;
    private String vocabListName;
    private LocalDateTime time;
    private List<TestContent> testContentList;

    public static TestResponseDto of(Test test){
        if(test.getType() == TestType.AUTO)
            return TestResponseDto.builder().testId(test.getId()).name(test.getName()).className(test.getUserClass().getName()).vocabListName(test.getVocabList().getName())
                    .time(test.getTime()).testContentList(test.getTestContentList()).build();
        else
            return TestResponseDto.builder().testId(test.getId()).name(test.getName()).className(test.getUserClass().getName())
                    .time(test.getTime()).testContentList(test.getTestContentList()).build();
    }

}
