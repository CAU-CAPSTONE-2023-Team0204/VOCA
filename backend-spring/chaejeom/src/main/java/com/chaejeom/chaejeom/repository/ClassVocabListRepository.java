package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.ClassVocabList;
import com.chaejeom.chaejeom.domain.UserClass;
import com.chaejeom.chaejeom.domain.VocabList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassVocabListRepository extends JpaRepository<ClassVocabList, Long> {
    Optional<ClassVocabList> findByVocabListAndUserClass(VocabList vocabList, UserClass userClass);

    boolean existsByVocabListAndUserClass(VocabList vocabList, UserClass userClass);
}
