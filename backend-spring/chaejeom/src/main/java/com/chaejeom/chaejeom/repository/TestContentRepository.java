package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.TestContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestContentRepository extends JpaRepository<TestContent, Long> {
}
