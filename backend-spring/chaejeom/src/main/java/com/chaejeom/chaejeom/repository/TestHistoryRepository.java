package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestHistory;
import com.chaejeom.chaejeom.domain.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    Optional<TestHistory> findByTest(Test test);
    List<TestHistory> findAllByUserClass(UserClass userClass);

}