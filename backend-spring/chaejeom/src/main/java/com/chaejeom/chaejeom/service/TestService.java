package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.*;
import com.chaejeom.chaejeom.dto.AutoTestRequestDto;
import com.chaejeom.chaejeom.dto.AutoTestResponseDto;
import com.chaejeom.chaejeom.dto.ManualTestRequestDto;
import com.chaejeom.chaejeom.dto.ManualTestResponseDto;
import com.chaejeom.chaejeom.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TestService {

    private final VocabListRepository vocabListRepository;
    private final VocabListContentRepository vocabListContentRepository;
    private final ClassVocabListRepository classVocabListRepository;
    private final ClassRepository classRepository;
    private final TestContentRepository testContentRepository;
    private final TestRepository testRepository;

    public AutoTestResponseDto createAutoTest(AutoTestRequestDto requestDto){
        UserClass userClass = classRepository.findById(requestDto.getClassId()).orElseThrow(()->new RuntimeException("해당 클래스가 없습니다."));
        VocabList vocabList = vocabListRepository.findById(requestDto.getVocabListId()).orElseThrow(()->new RuntimeException("해당 단어장이 없습니다."));

        int offset = 0;
        int number = requestDto.getNumber();

        Test test = Test.builder().name(requestDto.getName()).time(requestDto.getTime()).maxScore(requestDto.getNumber())
                .userclass(userClass).vocabList(vocabList).testContentList(new ArrayList<>()).build();

        List<VocabListContent> vocabListContents = vocabList.getVocabListContents();
        for(int i=offset; i<offset+number && i<vocabListContents.size(); i++){
            TestContent testContent = createTestContents(requestDto.getQuestionType(), vocabListContents.get(i).getWord(), vocabListContents.get(i).getMeaning());
            testContent.addTest(test);
            testContentRepository.save(testContent);
        }

        testRepository.save(test);

        AutoTestResponseDto responseDto = AutoTestResponseDto.of(test);

        return responseDto;
    }

    public ManualTestResponseDto createManualTest(ManualTestRequestDto requestDto){
        UserClass userClass = classRepository.findById(requestDto.getClassId()).orElseThrow(()->new RuntimeException("해당 클래스가 없습니다."));

        Test test = Test.builder().name(requestDto.getName()).time(requestDto.getTime()).maxScore(requestDto.getContents().size())
                .userclass(userClass).testContentList(new ArrayList<>()).build();


        for(int i = 0 ; i<requestDto.getContents().size(); i++){
            TestContent testContent = TestContent.builder().type(requestDto.getContents().get(i).getType()).question(requestDto.getContents().get(i).getQuestion())
                    .answer(requestDto.getContents().get(i).getAnswer()).build();
            testContent.addTest(test);
            testContentRepository.save(testContent);
        }
        testRepository.save(test);
        ManualTestResponseDto responseDto = ManualTestResponseDto.of(test);

        return responseDto;
    }
    private TestContent createTestContents(QuestionType type, String word, String meaning){
        TestContent testContent;
        switch(type){
            case ENG_TO_KOR :
                testContent = TestContent.builder().type(type).question(word).answer(meaning).build();
                break;
            case KOR_TO_ENG:
                testContent = TestContent.builder().type(type).question(meaning).answer(word).build();
                break;
            default:
                throw new RuntimeException("출제유형이 알맞지 않습니다.");
        }
        return testContent;
    }
}
