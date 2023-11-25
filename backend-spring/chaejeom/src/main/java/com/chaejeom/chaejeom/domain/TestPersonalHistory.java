package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TEST_PERSONAL_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class TestPersonalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_history_id")
    private Long id;

    @Column(name = "pass")
    private boolean pass;

    @Column(name = "exam_paper_image")
    private String image;

    @Column(name = "score")
    private int score;

    @Column(name = "max_score")
    private int maxScore;

    @Column(name = "score_hundred")
    private int hundredScore;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "test_history_id")
    private TestHistory testHistory;

    @OneToMany(mappedBy = "testPersonalHistory")
    private List<TestPersonalHistoryContent> testPersonalHistoryContentList = new ArrayList<>();

    public void addTestHistory(TestHistory testHistory){
        this.testHistory = testHistory;
        testHistory.getTestPersonalHistoryList().add(this);
        testHistory.addAttendCount();
    }

    public void addMember(Member member){
        this.member = member;
        member.getTestPersonalHistoryList().add(this);
    }

    public void setScore(){
        int sum = 0;
        for(TestPersonalHistoryContent content : testPersonalHistoryContentList){
            if(content.isResult()) sum++;
        }
        this.score = sum;
        setHundredScore();
    }
    public void setHundredScore(){
        this.hundredScore = (int)((double)score/(double) maxScore *100);
    }
    public void setPass(){
        if(hundredScore >= 90)
        {
            this.pass = true;
            this.testHistory.addPassCount();
        }
    }
}
