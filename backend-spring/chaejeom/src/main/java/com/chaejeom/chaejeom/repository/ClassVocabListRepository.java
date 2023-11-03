package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.ClassVocabList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassVocabListRepository extends JpaRepository<ClassVocabList, Long> {

}
