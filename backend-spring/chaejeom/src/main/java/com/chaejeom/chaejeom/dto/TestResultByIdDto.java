package com.chaejeom.chaejeom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultByIdDto {
    private String url;
    private String username;
    private String name;
    private int totalScore;
    private List<TestResultContentDto> contentList = new ArrayList<>();
}
