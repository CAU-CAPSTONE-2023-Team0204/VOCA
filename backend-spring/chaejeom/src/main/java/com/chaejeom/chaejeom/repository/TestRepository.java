package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.Test;
import com.chaejeom.chaejeom.domain.TestType;
import com.chaejeom.chaejeom.domain.UserClass;
import com.chaejeom.chaejeom.domain.VocabList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByName(String name);

    List<Test> findAllByVocabListAndUserClassAndType(VocabList vocabList, UserClass userClass, TestType type);
}
