package com.chaejeom.chaejeom.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// Member - UserClass 다대다 연결을 위한 연결테이블
@Entity
@Setter
@Getter
@Table(name = "MEMBER_CLASS")
public class MemberClass {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_class_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private UserClass userClass;

    public void addMember (Member member){
        this.member = member;
        member.getMemberClassList().add(this);
    }

    public void addClass(UserClass userClass){
        this.userClass = userClass;
        userClass.getMemberClassList().add(this);
    }
}
