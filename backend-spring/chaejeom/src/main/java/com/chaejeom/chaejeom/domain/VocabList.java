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

    @Lob
    @Column(name = "image")
    private String image;

    @JsonBackReference
    @OneToMany(mappedBy = "vocabList")
    private List<VocabListContent>  vocabListContents = new ArrayList<>();


}