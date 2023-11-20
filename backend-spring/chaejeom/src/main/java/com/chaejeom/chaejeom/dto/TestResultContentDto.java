package com.chaejeom.chaejeom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultContentDto {
    private Long contentId;
    private String question;
    private String answer;
    private String userAnswer;
    private boolean result;
}
