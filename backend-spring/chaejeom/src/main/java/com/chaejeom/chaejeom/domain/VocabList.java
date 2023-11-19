package com.chaejeom.chaejeom.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "voca_list")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class VocabList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voca_list_id")
    private Long id;

    @Column(nullable = false, name = "voca_list_name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    // ELEMENTARY, MIDDLE, HIGH
    @Enumerated(EnumType.STRING)
    private VocabCategory category;

    @JsonBackReference(value = "vocabList-contents")
    @OneToMany(mappedBy = "vocabList")
    private List<VocabListContent>  vocabListContents = new ArrayList<>();


}
