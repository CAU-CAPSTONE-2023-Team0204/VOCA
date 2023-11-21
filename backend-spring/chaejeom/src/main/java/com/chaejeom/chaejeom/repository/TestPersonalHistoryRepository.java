package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.TestHistory;
import com.chaejeom.chaejeom.domain.TestPersonalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestPersonalHistoryRepository extends JpaRepository<TestPersonalHistory, Long> {
    Optional<List<TestPersonalHistory>> findAllByMember(Member member);

    Optional<TestPersonalHistory> findByMemberAndTestHistory(Member member, TestHistory testHistory);
}