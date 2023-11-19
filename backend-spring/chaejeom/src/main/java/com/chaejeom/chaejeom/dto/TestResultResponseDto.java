package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultResponseDto {
    private Long testId;
    private List<PersonalResult> personalResultList = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class PersonalResult{
        private String url;
        private String username;
        private String name;
        private int totalScore;
        private List<Content> contentList = new ArrayList<>();

    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class Content{
        private Long contentId;
        private String question;
        private String answer;
        private String userAnswer;
        private boolean result;
    }

}

