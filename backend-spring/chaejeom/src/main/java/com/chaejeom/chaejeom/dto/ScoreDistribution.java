package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.TestPersonalHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreDistribution {
    private int belowForty;
    private int fifties;
    private int sixties;
    private int seventies;
    private int eighties;
    private int nineties;
    private int perfect;

    public static ScoreDistribution of(List<TestPersonalHistory> testPersonalHistoryList){
        int belowForty=0;
        int fifties=0;
        int sixties=0;
        int seventies=0;
        int eighties=0;
        int nineties=0;
        int perfect=0;
        for(TestPersonalHistory testPersonalHistory : testPersonalHistoryList){
            int score = testPersonalHistory.getHundredScore();
            if(score == 100){
                perfect++;
            } else if (score >= 90) {
                nineties++;
            } else if(score >= 80){
                eighties++;
            } else if (score >= 70) {
                seventies++;
            } else if (score >= 60) {
                sixties++;
            } else if(score >= 50){
                fifties++;
            } else {
                belowForty++;
            }
        }
        return new ScoreDistribution(belowForty,fifties,sixties,seventies,eighties,nineties,perfect);
    }
}
