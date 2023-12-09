package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<UserClass, Long> {
    Optional<UserClass> findByName(String name);
}
