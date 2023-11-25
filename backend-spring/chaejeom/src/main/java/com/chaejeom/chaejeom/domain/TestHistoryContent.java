package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TEST_HISTORY_CONTENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class TestHistoryContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_history_content_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_content_id")
    private TestContent testContent;

    @ManyToOne
    @JoinColumn(name = "test_history_id")
    private TestHistory testHistory;

    @OneToMany(mappedBy = "testHistoryContent")
    private List<TestPersonalHistoryContent> testPersonalHistoryContentList = new ArrayList<>();

    @Column(name = "rate")
    private double rate;

    public void addTestHistory(TestHistory testHistory){
        this.testHistory =testHistory;
        testHistory.getTestHistoryContentList().add(this);
    }
    public void setRate(){
        double total = testPersonalHistoryContentList.size();
        double count = 0;
        for(TestPersonalHistoryContent content : testPersonalHistoryContentList){
            if(content.isResult()) count ++;
        }
        this.rate = count/total *100;
    }

}
