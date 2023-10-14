package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.Role;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    private void after(){
        em.clear();
    }
    @Test
    public void 회원저장_성공() throws Exception {
        //given
        Member member = Member.builder().username("username").password("1234567890").name("Member1").email("123@test.com").role(Role.STUDENT).build();

        //when
        Member saveMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(saveMember.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다"));//아직 예외 클래스를 만들지 않았기에 RuntimeException으로 처리하겠습니다.

        assertThat(findMember).isSameAs(saveMember);
        assertThat(findMember).isSameAs(member);
    }

    @Test
    public void 오류_회원가입시_아이디가_없음() throws Exception {
        //given
        Member member = Member.builder().password("1234567890").name("Member1").email("email").role(Role.TEACHER).build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    @Test
    public void 오류_회원가입시_이름이_없음() throws Exception {
        //given
        Member member = Member.builder().username("username").password("1234567890").email("email").role(Role.STUDENT).build();

        //when, then
        assertThrows(Exception.class, () -> memberRepository.save(member));
    }
}