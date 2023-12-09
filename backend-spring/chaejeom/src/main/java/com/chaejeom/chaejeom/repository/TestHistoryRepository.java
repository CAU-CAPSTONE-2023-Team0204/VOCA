package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestHistory;
import com.chaejeom.chaejeom.domain.UserClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    Optional<TestHistory> findByTest(Test test);
    List<TestHistory> findAllByUserClass(UserClass userClass);

    //@Query("SELECT tk FROM Member m JOIN m.testPersonalHistoryList ptk JOIN ptk.testHistory tk JOIN tk.test t WHERE m.id = :memberId AND t.date = (SELECT MAX(ptk2.date) FROM Member m2 JOIN m2.testPersonalHistoryList ptk2 JOIN ptk2.testHistory tk2 JOIN tk2.test t2 WHERE m2.id = m.id)")
    @Query("SELECT p.testHistory FROM TestPersonalHistory p WHERE p.member.id = :memberId ORDER BY p.testHistory.dateTime DESC")
    List<TestHistory> findLatestTestHitoryByMemberID(@Param("memberId") Long memberId, Pageable pageable);
}