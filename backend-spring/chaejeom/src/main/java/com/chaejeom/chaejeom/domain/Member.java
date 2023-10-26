package com.chaejeom.chaejeom.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class Member{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // primary key

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 유저가 설정한 id

    private String password; // 비밀번호

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role; // 권한 -> ROLE_STUDENT, ROLE_TEACHER

    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<MemberClass> memberClassList = new ArrayList<MemberClass>();


    // 정보 수정 //
    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateEmail(String email){
        this.email = email;
    }

    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    //클래스 등록//
    public void addMemberClass (MemberClass memberClass){
        memberClassList.add(memberClass);
        memberClass.setMember(this);
    }
}
