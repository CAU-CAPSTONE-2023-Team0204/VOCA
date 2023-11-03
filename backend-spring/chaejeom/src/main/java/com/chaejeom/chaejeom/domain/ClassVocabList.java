package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "class_vocablist")
public class ClassVocabList {
    @Id
    @GeneratedValue
    @Column(name = "class_vocab_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private UserClass userClass;

    @ManyToOne
    @JoinColumn(name = "voca_list_id")
    private VocabList vocabList;

    public void addUserClass (UserClass userClass){
        this.userClass = userClass;
        userClass.getClassVocabLists().add(this);
    }

    public void addVocabList (VocabList vocabList){
        this.vocabList = vocabList;
    }
}
