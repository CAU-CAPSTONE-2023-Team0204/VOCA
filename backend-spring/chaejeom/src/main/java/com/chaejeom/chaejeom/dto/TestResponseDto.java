package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestContent;
import com.chaejeom.chaejeom.domain.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponseDto {
    private Long testId;
    private String name;
    private Long classId;
    private String className;
    private Long vocabListId;
    private String vocabListName;
    private LocalDate date;
    private List<TestContent> testContentList;

    public static TestResponseDto of(Test test){
        if(test.getType() == TestType.AUTO)
            return TestResponseDto.builder().testId(test.getId()).name(test.getName()).classId(test.getUserClass().getId()).className(test.getUserClass().getName()).vocabListName(test.getVocabList().getName())
                    .vocabListId(test.getVocabList().getId()).date(test.getDate()).testContentList(test.getTestContentList()).build();
        else
            return TestResponseDto.builder().testId(test.getId()).name(test.getName()).classId(test.getUserClass().getId()).className(test.getUserClass().getName())
                    .date(test.getDate()).testContentList(test.getTestContentList()).build();
    }

}
