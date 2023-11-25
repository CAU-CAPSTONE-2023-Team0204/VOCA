package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.TestContent;
import com.chaejeom.chaejeom.domain.TestHistoryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestHistoryContentRepository extends JpaRepository<TestHistoryContent, Long> {
    Optional<TestHistoryContent> findByTestContent (TestContent testContent);
}
