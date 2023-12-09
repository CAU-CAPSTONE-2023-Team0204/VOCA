package com.chaejeom.chaejeom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LatestTestInfoDto {
    //가장 최근 시험의 날짜, 점수대별 분포, 평균 정답률, 통과자율, 응시율

    private LocalDate date;
    private double average;
    private double passRate;
    private double attendRate;
    private ScoreDistribution scoreDistribution;
}