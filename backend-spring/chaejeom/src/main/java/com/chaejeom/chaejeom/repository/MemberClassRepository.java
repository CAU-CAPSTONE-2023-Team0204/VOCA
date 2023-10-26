package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.MemberClass;
import com.chaejeom.chaejeom.domain.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberClassRepository extends JpaRepository<MemberClass, Long> {
    Optional<MemberClass> findByMemberAndUserClass(Member member, UserClass userClass);
    boolean existsByMemberAndUserClass(Member member, UserClass userClass);

}
