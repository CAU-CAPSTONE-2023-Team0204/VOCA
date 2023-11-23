package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.VocabListContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabListContentRepository extends JpaRepository<VocabListContent ,Long> {

}
