package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.TestPersonalHistoryContent;
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

    public static TestResultContentDto of(TestPersonalHistoryContent testPersonalHistoryContent) {
        return TestResultContentDto.builder()
                .contentId(testPersonalHistoryContent.getId())
                .question(testPersonalHistoryContent.getQuestion())
                .answer(testPersonalHistoryContent.getAnswer())
                .userAnswer(testPersonalHistoryContent.getSubmit())
                .result(testPersonalHistoryContent.isResult()).build();
    }
}
