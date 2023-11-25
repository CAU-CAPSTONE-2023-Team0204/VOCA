package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.TestPersonalHistoryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPersonalHistoryContentRepository extends JpaRepository<TestPersonalHistoryContent, Long> {

}