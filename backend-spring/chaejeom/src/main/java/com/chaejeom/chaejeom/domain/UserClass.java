package com.chaejeom.chaejeom.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// class 라는 명칭은 자바의 클래스와 겹쳐서 UserClass로 변경하였음
@Table(name = "class")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
public class UserClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "userClass")
    private List<MemberClass> memberClassList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "userClass")
    private List<ClassVocabList> classVocabLists = new ArrayList<>();

    @Builder
    public UserClass(String name){
        this.name = name;
        memberClassList = new ArrayList<>();
    }

}
