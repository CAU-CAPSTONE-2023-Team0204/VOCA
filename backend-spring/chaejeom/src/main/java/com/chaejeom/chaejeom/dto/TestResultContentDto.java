package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.TestHistoryContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResultContentDto {
    private Long contentId;
    private String question;
    private String answer;
    private String userAnswer;
    private boolean result;

    public static TestResultContentDto of(TestHistoryContent testHistoryContent) {
        return TestResultContentDto.builder()
                .contentId(testHistoryContent.getId())
                .question(testHistoryContent.getQuestion())
                .answer(testHistoryContent.getAnswer())
                .userAnswer(testHistoryContent.getSubmit())
                .result(testHistoryContent.isResult()).build();
    }
}
