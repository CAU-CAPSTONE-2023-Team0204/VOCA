package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutoTestRequestDto {
    private String name;
    private LocalDate date;
    private Long vocabListId;
    private Long classId;
    private int number;
    private QuestionType questionType;

}
