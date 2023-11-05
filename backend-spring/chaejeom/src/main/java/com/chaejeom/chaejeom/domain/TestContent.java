package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "TEST_CONTENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class TestContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_content_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type",nullable = false, length = 30)
    private QuestionType type;

    @Column
    private int score;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
