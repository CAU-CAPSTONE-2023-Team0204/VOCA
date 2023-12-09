package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "TEST_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class TestHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_history_id")
    private Long id;

    @Column(name = "total_exam_paper_image")
    private String image;

    @Column(name = "max_score")
    private int maxScore;

    @Column(name = "average")
    private double average;

    @Column(name = "attend_count")
    private int attendCount=0;

    @Column(name = "pass_count")
    private int passCount=0;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private UserClass userClass;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "testHistory")
    private List<TestPersonalHistory> testPersonalHistoryList = new ArrayList<>();
    @OneToMany(mappedBy = "testHistory")
    private List<TestHistoryContent> testHistoryContentList = new ArrayList<>();

    public void setAverage(){
        double count = 0;
        double sum = 0;

        for(TestPersonalHistory e : testPersonalHistoryList){
            count++;
            sum += e.getHundredScore();
        }
        this.average = sum/count;
    }

    public void addAttendCount(){
        this.attendCount++;
    }
    public void addPassCount(){
        this.passCount++;
    }
    public void setCorrectRate(){
        for(TestHistoryContent testHistoryContent : testHistoryContentList){
            testHistoryContent.setRate();
        }
    }

}
