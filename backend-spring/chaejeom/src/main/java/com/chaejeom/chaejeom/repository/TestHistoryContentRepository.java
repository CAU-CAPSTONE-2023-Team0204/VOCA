package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.TestHistoryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestHistoryContentRepository extends JpaRepository<TestHistoryContent, Long> {

}