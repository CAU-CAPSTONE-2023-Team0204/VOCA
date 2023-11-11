package com.chaejeom.chaejeom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestListResponseDto {
    private Long userId;
    private String userName;
    private Long classId;
    private String className;

    private List<TestResponseDto> tests;
}
