package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByName(String name);
}
