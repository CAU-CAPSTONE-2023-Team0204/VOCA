package com.chaejeom.chaejeom.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "voca_list_content")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
public class VocabListContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voca_content_id")
    private Long id;

    @Column(nullable = false, name = "word")
    private String word;

    @Column(nullable = false, name = "meaning")
    private String meaning;

    @Column(name = "meaning_eng")
    private String meaning_eng;

    @JsonIgnore
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "voca_list_id")
    private VocabList vocabList;

    public void addContent(VocabList vocabList){
        this.vocabList = vocabList;
        vocabList.getVocabListContents().add(this);
    }

}
