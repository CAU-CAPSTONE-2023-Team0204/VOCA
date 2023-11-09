package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestContentRequestDto {
        private QuestionType type;
        private String question;
        private String answer;
}
