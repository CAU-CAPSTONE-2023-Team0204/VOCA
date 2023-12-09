package com.chaejeom.chaejeom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResultListDto {
    private String url;
    private Long memberId;
    private String username;
    private String name;
    private boolean pass;
    private int totalScore;
    private int maxScore;
    private int hundredScore;

}
