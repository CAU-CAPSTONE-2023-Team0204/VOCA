package com.chaejeom.chaejeom.domain;

import com.chaejeom.chaejeom.dto.TestHistoryContentUpdateDto;
import jakarta.persistence.*;
import lombok.*;

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

    @Column
    private String question;
    // 제출한 답
    @Column
    private String submit;

    // 정답
    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private boolean result;

    @ManyToOne
    @JoinColumn(name = "personal_history_id")
    private TestPersonalHistory testPersonalHistory;

    public void addPersonalHistory(TestPersonalHistory testPersonalHistory){
        this.testPersonalHistory = testPersonalHistory;
        testPersonalHistory.getTestHistoryContentList().add(this);
    }

    public void update(TestHistoryContentUpdateDto request){
        this.question = request.getQuestion();
        this.submit = request.getUserAnswer();
        this.answer = request.getAnswer();
        this.result = request.isResult();

        testPersonalHistory.setScore();
        testPersonalHistory.getTestHistory().setAverage();
    }
}
