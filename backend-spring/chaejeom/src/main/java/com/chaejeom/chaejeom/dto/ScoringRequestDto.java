package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.TestContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoringRequestDto {
    private Long testID;
    private Long classID;
    private String file;
    private List<TestContent> testContentList = new ArrayList<>();
    private List<Member> memberList = new ArrayList<>();
}
