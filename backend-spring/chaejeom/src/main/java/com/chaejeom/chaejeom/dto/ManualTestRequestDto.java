package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManualTestRequestDto {
    private String name;
    private LocalDateTime time;
    private Long classId;
    private QuestionType questionType;
    private List<TestContentRequestDto> contents = new ArrayList<>();

}
