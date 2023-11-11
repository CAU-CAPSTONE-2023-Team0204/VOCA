package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.*;
import com.chaejeom.chaejeom.dto.*;
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

    private final MemberRepository memberRepository;
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
                .vocabList(vocabList).type(TestType.AUTO).testContentList(new ArrayList<>()).build();

        test.addUserClass(userClass);

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

        Test test = Test.builder().name(requestDto.getName()).time(requestDto.getTime()).maxScore(requestDto.getContents().size()).type(TestType.MANUAL)
                .testContentList(new ArrayList<>()).build();
        test.addUserClass(userClass);


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

    public TestListResponseDto findTestByUserId(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 유저가 없습니다."));

        List<MemberClass> memberClassList = member.getMemberClassList();
        List<Test> testList = new ArrayList<>();
        List<TestResponseDto> tests = new ArrayList<>();

        for(MemberClass memberClass : memberClassList){
            testList.addAll(memberClass.getUserClass().getTestList());
        }

        for (Test test : testList){
            tests.add(TestResponseDto.of(test));
        }

        TestListResponseDto testListResponseDto = TestListResponseDto.builder().userId(id).userName(member.getUsername()).tests(tests).build();

        return testListResponseDto;
    }

    public TestListResponseDto findTodoTestByUserId(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 유저가 없습니다."));

        List<MemberClass> memberClassList = member.getMemberClassList();
        List<Test> testList = new ArrayList<>();
        List<TestResponseDto> tests = new ArrayList<>();

        for(MemberClass memberClass : memberClassList){
            testList.addAll(memberClass.getUserClass().getTestList());
        }

        for (Test test : testList){
            if(test.isStatus() == false)
            tests.add(TestResponseDto.of(test));
        }

        TestListResponseDto testListResponseDto = TestListResponseDto.builder().userId(id).userName(member.getUsername()).tests(tests).build();

        return testListResponseDto;
    }

    public TestListResponseDto findTestByClassId(Long id){
        UserClass userClass = classRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 클래스가 없습니다."));

        List<TestResponseDto> tests = new ArrayList<>();

        for (Test test : userClass.getTestList()){
            tests.add(TestResponseDto.of(test));
        }

        TestListResponseDto testListResponseDto = TestListResponseDto.builder().classId(userClass.getId()).className(userClass.getName()).tests(tests).build();

        return testListResponseDto;

    }

    public TestListResponseDto findTodoTestByClassId(Long id){
        UserClass userClass = classRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 클래스가 없습니다."));

        List<TestResponseDto> tests = new ArrayList<>();

        for (Test test : userClass.getTestList()){
            if (test.isStatus() == false)
            tests.add(TestResponseDto.of(test));
        }

        TestListResponseDto testListResponseDto = TestListResponseDto.builder().classId(userClass.getId()).className(userClass.getName()).tests(tests).build();

        return testListResponseDto;

    }

}
