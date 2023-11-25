package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.TestPersonalHistoryContent;
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
        for(TestPersonalHistoryContent testPersonalHistoryContent : testPersonalHistory.getTestPersonalHistoryContentList()){
            contentDtoList.add(TestResultContentDto.of(testPersonalHistoryContent));
        }
        return TestResultPersonalDto.builder().url(testPersonalHistory.getImage()).name(testPersonalHistory.getMember().getName())
                .username(testPersonalHistory.getMember().getUsername()).totalScore(testPersonalHistory.getScore())
                .contentList(contentDtoList).build();
    }
}
