package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.VocabList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabListRepository extends JpaRepository<VocabList, Long> {

}
