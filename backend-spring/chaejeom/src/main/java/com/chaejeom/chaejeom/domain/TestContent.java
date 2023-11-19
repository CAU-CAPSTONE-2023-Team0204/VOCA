package com.chaejeom.chaejeom.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "question_type",nullable = false)
    private QuestionType type;
    private String question;
    private String answer;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    public void addTest (Test test){
        this.test = test;
        test.getTestContentList().add(this);
    }
}
