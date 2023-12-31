package com.chaejeom.chaejeom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "TEST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class Test extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

    @Column(name = "test_name")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "test_date")
    private LocalDate date;

    @Column(name = "max_score")
    private int maxScore;

    @Column(name = "pass_score")
    private int passScore;

    // 시험 시행 여부 true :시행된 시험, false : 시행 예정 시험
    @Column(name = "test_status")
    @ColumnDefault("false")
    private boolean status;

    @Column(name = "test_type")
    @Enumerated(EnumType.STRING)
    private TestType type;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private UserClass userClass;

    // 단어장 기반으로 자동 출제된 시험의 경우 vocabList 값을 가진다.
    @ManyToOne
    @JoinColumn(name = "voca_list_id")
    private VocabList vocabList;

    @OneToMany(mappedBy = "test")
    private List<TestContent> testContentList = new ArrayList<>();

    public void addUserClass(UserClass userClass){
        this.userClass =userClass;
        userClass.getTestList().add(this);
    }

    public void endTest(){
        this.status = true;
    }

    public void setMaxScore(){
        this.maxScore = testContentList.size();
    }
    public void setPassScore(int passScore){
        this.passScore = passScore;
    }

}
