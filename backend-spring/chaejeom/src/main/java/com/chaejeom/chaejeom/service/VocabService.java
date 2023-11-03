package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.repository.ClassVocabListRepository;
import com.chaejeom.chaejeom.repository.VocabListContentRepository;
import com.chaejeom.chaejeom.repository.VocabListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class VocabService {
    private final VocabListRepository vocabListRepository;
    private final VocabListContentRepository vocabListContentRepository;
    private final ClassVocabListRepository classVocabListRepository;



}
