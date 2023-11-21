package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.TestHistoryContent;
import com.chaejeom.chaejeom.domain.TestPersonalHistory;
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
public class TestResultPersonalDto {
    private String url;
    private String username;
    private String name;
    private int totalScore;
    private List<TestResultContentDto> contentList = new ArrayList<>();


    public static TestResultPersonalDto of(TestPersonalHistory testPersonalHistory){
        List<TestResultContentDto> contentDtoList = new ArrayList<>();
        for(TestHistoryContent testHistoryContent : testPersonalHistory.getTestHistoryContentList()){
            contentDtoList.add(TestResultContentDto.of(testHistoryContent));
        }
        return TestResultPersonalDto.builder().url(testPersonalHistory.getImage()).name(testPersonalHistory.getMember().getName())
                .username(testPersonalHistory.getMember().getUsername()).totalScore(testPersonalHistory.getScore())
                .contentList(contentDtoList).build();
    }
}
