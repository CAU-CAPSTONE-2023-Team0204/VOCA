package com.chaejeom.chaejeom.dto;

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
    private List<TestResultPersonalDto> personalResultList = new ArrayList<>();


}