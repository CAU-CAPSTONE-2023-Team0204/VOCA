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

    @Column(name = "total_exam_paper_image")
    private String image;

    @Column(name = "max_score")
    private int maxScore;

    @Column(name = "average")
    private double average;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private UserClass userClass;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
