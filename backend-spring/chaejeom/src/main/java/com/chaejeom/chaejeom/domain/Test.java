package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "TEST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

    @Column(name = "test_name")
    private String name;

    @Column(name = "test_time")
    private LocalDateTime time;

    @Column(name = "max_score")
    private int maxScore;

    @ManyToOne
    @JoinColumn(name = "voca_list_id")
    private VocabList vocabList;

}
