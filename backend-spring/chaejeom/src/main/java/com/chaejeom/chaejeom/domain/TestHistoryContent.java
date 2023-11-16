package com.chaejeom.chaejeom.domain;

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

    // 제출한 답
    @Column
    private String submit;

    // 정답
    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private boolean result;

    @ManyToOne
    @JoinColumn(name = "test_history_id")
    private TestHistory testHistory;
}