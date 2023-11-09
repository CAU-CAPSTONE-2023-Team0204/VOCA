package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "pass")
    private boolean pass;

    @Column(name = "exam_paper_image")
    private String image;

    @Column(name = "total_score")
    private int score;

    @Column(name = "max_score")
    private int maxScore;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
