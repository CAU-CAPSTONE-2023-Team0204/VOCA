package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.*;
import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;
    private final VocabListRepository vocabListRepository;
    private final VocabListContentRepository vocabListContentRepository;
    private final ClassRepository classRepository;
    private final TestContentRepository testContentRepository;
    private final TestRepository testRepository;
    private final TestHistoryRepository testHistoryRepository;
    private final TestPersonalHistoryRepository testPersonalHistoryRepository;
    private final TestPersonalHistoryContentRepository testPersonalHistoryContentRepository;
    private final TestHistoryContentRepository testHistoryContentRepository;

    private final S3UploadService s3UploadService;

    public AutoTestResponseDto createAutoTest(AutoTestRequestDto requestDto){
        UserClass userClass = classRepository.findById(requestDto.getClassId()).orElseThrow(()->new RuntimeException("해당 클래스가 없습니다."));
        VocabList vocabList = vocabListRepository.findById(requestDto.getVocabListId()).orElseThrow(()->new RuntimeException("해당 단어장이 없습니다."));
        int offset = 0;
        int number = requestDto.getNumber();

        List<Test> testList = testRepository.findAllByVocabListAndUserClassAndType(vocabList, userClass, TestType.AUTO);
        if(!testList.isEmpty()) {
            Test latestTest = testList.get(0);
            LocalDate latestDate = LocalDate.MIN;
            for (Test test : testList) {
                if (test.getDate().isAfter(latestDate) ||test.getDate().isEqual(latestDate)){
                    latestTest = test;
                    latestDate = test.getDate();
                }
            }
            TestContent latestContent = latestTest.getTestContentList().get(latestTest.getTestContentList().size()-1);
            String word = "";
            if(latestContent.getType() == QuestionType.KOR_TO_ENG){
                word = latestContent.getAnswer();
            }
            else word = latestContent.getQuestion();

            VocabListContent vocabListContent = vocabListContentRepository.findByWord(word).orElseThrow(()->new RuntimeException("auto test 마지막 문제 찾기 에러"));
            offset = vocabListContent.getId().intValue() - vocabList.getVocabListContents().get(0).getId().intValue();
        }

        Test test = Test.builder().name(requestDto.getName()).date(requestDto.getDate()).maxScore(requestDto.getNumber()).passScore(requestDto.getPass_score())
                .vocabList(vocabList).type(TestType.AUTO).testContentList(new ArrayList<>()).build();

        test.addUserClass(userClass);

        List<VocabListContent> vocabListContents = vocabList.getVocabListContents();
        for(int i=offset; i<offset+number && i<vocabListContents.size(); i++){
            TestContent testContent = createTestContents(i%2 == 0? QuestionType.ENG_TO_KOR : QuestionType.KOR_TO_ENG, vocabListContents.get(i).getWord(), vocabListContents.get(i).getMeaning());
            testContent.addTest(test);
            testContentRepository.save(testContent);
        }

        testRepository.save(test);

        AutoTestResponseDto responseDto = AutoTestResponseDto.of(test);

        return responseDto;
    }

    public ManualTestResponseDto createManualTest(ManualTestRequestDto requestDto){
        UserClass userClass = classRepository.findById(requestDto.getClassId()).orElseThrow(()->new RuntimeException("해당 클래스가 없습니다."));

        Test test = Test.builder().name(requestDto.getName()).date(requestDto.getDate()).maxScore(requestDto.getContents().size()).type(TestType.MANUAL)
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

    // 오답 기반 출제
    public CustomTestResponseDto createCustomTest(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new RuntimeException("해당 유저가 없습니다."));

        List<TestPersonalHistory> testPersonalHistoryList = testPersonalHistoryRepository.findAllByMember(member).orElseThrow(()->new RuntimeException("해당 유저의 지난 결과가 없습니다."));

        Test test = Test.builder().name(LocalDate.now().toString() + member.getUsername()+"오답시험").type(TestType.CUSTOM).testContentList(new ArrayList<>()).build();

        for(TestPersonalHistory e : testPersonalHistoryList){
            for(TestPersonalHistoryContent content : e.getTestPersonalHistoryContentList()){
                if(!content.isResult()){
                    TestContent testContent = TestContent.builder().type(content.getTestHistoryContent().getTestContent().getType())
                            .question(content.getQuestion()).answer(content.getAnswer()).build();
                    testContent.addTest(test);
                    testContentRepository.save(testContent);
                }
            }
        }
        test.setMaxScore();
        test.setPassScore(test.getMaxScore());
        testRepository.save(test);
        return CustomTestResponseDto.of(test, member);
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
            if(test.isStatus() == false) {
                tests.add(TestResponseDto.of(test));
            }
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
    public TestResponseDto findTestByTestId(long id){
        Test test = testRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));

        return TestResponseDto.of(test);
    }

    public TestListResponseDto findTodoTestByClassId(Long id){
        UserClass userClass = classRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 클래스가 없습니다."));

        List<TestResponseDto> tests = new ArrayList<>();

        for (Test test : userClass.getTestList()){
            if (test.isStatus() == false) {
                tests.add(TestResponseDto.of(test));
            }
        }

        TestListResponseDto testListResponseDto = TestListResponseDto.builder().classId(userClass.getId()).className(userClass.getName()).tests(tests).build();

        return testListResponseDto;

    }


    // 클라이언트에서 시험id와 시험지 파일을 받아 클라우드에 저장하고, 파일과 시험 정보를 장고 서버에 전달. 채점 완료된 정보를 json 형태로 반환받아 객체에 저장후 반환.
    public TestResultResponseDto scoring(MultipartFile multipartFile, Long testId) throws IOException {
        Test test = testRepository.findById(testId).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));
        // 시험지 pdf파일 저장
        String originalPdfUrl = s3UploadService.saveFile(multipartFile, "test_content_"+test.getId().toString()+"_");

        // pdf to png list
        InputStream is = multipartFile.getInputStream();
        List<String> contentList = conversionPdf2Img(is, "test_content_"+test.getId().toString()+"_");

        //시험치는 클래스의 학생 리스트
        List<MemberClass> memberClassList = test.getUserClass().getMemberClassList();
        List<Member> memberList = new ArrayList<>();
        for(MemberClass memberClass : memberClassList){
            memberList.add(memberClass.getMember());
        }

        //request dto 생성
        ScoringRequestDto scoringRequestDto = ScoringRequestDto.builder().testID(test.getId()).classID(test.getUserClass().getId()).testContentList(test.getTestContentList())
                .file(contentList).memberList(new ArrayList<>())
                .build();
        scoringRequestDto.setMemberList(memberList);

        //ai 파트와 통신 후 채점 결과 받기
        final String djangoURL = "https://sehwanii.pythonanywhere.com/main/";

        RestTemplate restTemplate = new RestTemplate();
        TestResultResponseDto resultResponse= restTemplate.postForObject(djangoURL, scoringRequestDto, TestResultResponseDto.class);


        // 시험 결과 데이터 처리 //
        if(resultResponse.getTestId() != test.getId()) throw new RuntimeException("채점 결과의 시험 id가 올바르지 않습니다.");
        //test history 생성
        TestHistory testHistory = TestHistory.builder().test(test).image(originalPdfUrl).userClass(test.getUserClass())
                .testPersonalHistoryList(new ArrayList<>())
                .testHistoryContentList(new ArrayList<>())
                .dateTime(LocalDateTime.now())
                .maxScore(test.getMaxScore()).build();

        testHistoryRepository.save(testHistory);

        for(TestContent testContent :test.getTestContentList()){ // test content list 에 따라 test history content 테이블에 추가한다.
            TestHistoryContent testHistoryContent = TestHistoryContent.builder().testContent(testContent).testPersonalHistoryContentList(new ArrayList<>()).build();
            testHistoryContent.addTestHistory(testHistory);
            testHistoryContentRepository.save(testHistoryContent);
        }
        // ****시험 pass/fail 추가 필요.
        // 시험 결과를 db에 저장한다.
        for(TestResultPersonalDto personalDto : resultResponse.getPersonalResultList()){
            // member 수 만큼 personal history 생성
            Member member = memberRepository.findByUsername(personalDto.getUsername()).orElseThrow(()-> new RuntimeException("채점 결과의 학생을 찾을 수 없습니다."));
            TestPersonalHistory testPersonalHistory = TestPersonalHistory.builder().score(personalDto.getTotalScore()).maxScore(test.getMaxScore())
                    .image(personalDto.getUrl()).testPersonalHistoryContentList(new ArrayList<>()).build();
            testPersonalHistory.addMember(member);
            testPersonalHistory.addTestHistory(testHistory);
            testPersonalHistoryRepository.save(testPersonalHistory);

            // content list만큼 personal history content 생성
            for(TestResultContentDto contentDto : personalDto.getContentList()){
                TestContent testContent = testContentRepository.findById(contentDto.getContentId()).orElseThrow(()-> new RuntimeException("해당 testcontent가없음"));
                TestHistoryContent historyContent = testHistoryContentRepository.findByTestContent(testContent).orElseThrow(()-> new RuntimeException("해당 test content history가 존재하지 않습니다."));
                TestPersonalHistoryContent content = TestPersonalHistoryContent.builder()
                        .answer(contentDto.getAnswer())
                        .result(contentDto.isResult())
                        .submit(contentDto.getUserAnswer())
                        .question(contentDto.getQuestion())
                        .build();
                content.addContentHistory(historyContent);
                content.addPersonalHistory(testPersonalHistory);
                testPersonalHistoryContentRepository.save(content);
            }
            testPersonalHistory.setHundredScore();
            testPersonalHistory.setPass();
        }
        //결과 -> 평균 계산
        testHistory.setAverage();
        testHistory.setCorrectRate();
        // 테스트 종료
        test.endTest();

        return resultResponse;
    }


    // 클라이언트에서 시험 id 와 시험지 파일을 받아 저장하고, 파일과 시험문제 정보를 반환하는 테스트함수.
    public ScoringRequestDto scoringTest(MultipartFile multipartFile, Long testId) throws IOException {
        Test test = testRepository.findById(testId).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));
        // 시험지 pdf파일 저장

        // pdf to png list
        InputStream is = multipartFile.getInputStream();
        List<String> contentList = conversionPdf2Img(is, "test_content_"+test.getId().toString()+"_");

        //시험치는 클래스의 학생 리스트
        List<MemberClass> memberClassList = test.getUserClass().getMemberClassList();
        List<Member> memberList = new ArrayList<>();
        for(MemberClass memberClass : memberClassList){
            memberList.add(memberClass.getMember());
        }

        //request dto 생성
        ScoringRequestDto scoringRequestDto = ScoringRequestDto.builder().testID(test.getId()).classID(test.getUserClass().getId()).testContentList(test.getTestContentList())
                .file(contentList).memberList(new ArrayList<>())
                .build();
        scoringRequestDto.setMemberList(memberList);

        return scoringRequestDto;
    }

    public TestResultResponseDto getResultTest(TestResultResponseDto resultResponse){
        // 시험 결과 데이터 처리 //
        Test test = testRepository.findById(resultResponse.getTestId()).orElseThrow(()-> new RuntimeException("시험이 존재하지않음"));
        TestHistory testHistory = TestHistory.builder().test(test).image("originalPdfUrl").userClass(test.getUserClass()).testPersonalHistoryList(new ArrayList<>()).testHistoryContentList(new ArrayList<>())
                .dateTime(LocalDateTime.now()).maxScore(test.getMaxScore()).build();
        testHistoryRepository.save(testHistory);

        for(TestContent testContent :test.getTestContentList()){ // test content list 에 따라 test history content 테이블에 추가한다.
            TestHistoryContent testHistoryContent = TestHistoryContent.builder().testContent(testContent).testPersonalHistoryContentList(new ArrayList<>()).build();
            testHistoryContent.addTestHistory(testHistory);
            testHistoryContentRepository.save(testHistoryContent);
        }

        // ****시험 pass/fail 추가 필요.
        // 시험 결과를 db에 저장한다.
        for(TestResultPersonalDto personalDto : resultResponse.getPersonalResultList()){
            // member 수 만큼 personal history 생성
            Member member = memberRepository.findByUsername(personalDto.getUsername()).orElseThrow(()-> new RuntimeException("채점 결과의 학생을 찾을 수 없습니다."));
            TestPersonalHistory testPersonalHistory = TestPersonalHistory.builder().score(personalDto.getTotalScore()).maxScore(test.getMaxScore())
                    .image(personalDto.getUrl()).testPersonalHistoryContentList(new ArrayList<>()).build();
            testPersonalHistory.addMember(member);
            testPersonalHistory.addTestHistory(testHistory);
            testPersonalHistoryRepository.save(testPersonalHistory);

            // content list만큼 personal history content 생성
            for(TestResultContentDto contentDto : personalDto.getContentList()){
                TestContent testContent = testContentRepository.findById(contentDto.getContentId()).orElseThrow(()-> new RuntimeException("해당 testcontent가없음"));
                TestHistoryContent historyContent = testHistoryContentRepository.findByTestContent(testContent).orElseThrow(()-> new RuntimeException("해당 test content history가 존재하지 않습니다."));
                TestPersonalHistoryContent content = TestPersonalHistoryContent.builder()
                        .answer(contentDto.getAnswer())
                        .result(contentDto.isResult())
                        .submit(contentDto.getUserAnswer())
                        .question(contentDto.getQuestion())
                        .build();
                content.addContentHistory(historyContent);
                content.addPersonalHistory(testPersonalHistory);
                testPersonalHistoryContentRepository.save(content);
            }
            testPersonalHistory.setHundredScore();
            testPersonalHistory.setPass();
        }
        //결과 -> 평균 계산
        testHistory.setAverage();
        testHistory.setCorrectRate();
        // 테스트 종료
        test.endTest();

        return resultResponse;
    }

    public TestResultResponseDto testToDjango (ScoringRequestDto scoringRequestDto){
        //ai 파트와 통신 후 채점 결과 받기
        final String djangoURL = "https://sehwanii.pythonanywhere.com/main/";

        RestTemplate restTemplate = new RestTemplate();
        TestResultResponseDto resultResponse= restTemplate.postForObject(djangoURL, scoringRequestDto, TestResultResponseDto.class);

        return resultResponse;
    }

    // pdf를 이미지로 바꾸어 s3 버킷에 저장하고 url List 를 반환한다.
    private List<String> conversionPdf2Img(InputStream is, String uniqueId) {
        List<String> savedImgList = new ArrayList<>(); //저장된 이미지 경로 list
        try {
            PDDocument pdfDoc = PDDocument.load(is); //Document 생성
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);

            // PDF페이지 루프
            for (int i=0; i<pdfDoc.getPages().getCount(); i++) {
                String imgFileName = uniqueId + "-" + i ;

                //DPI 설정
                BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 150, ImageType.GRAY);

                ByteArrayOutputStream baos =  new ByteArrayOutputStream();
                ImageIO.write(bim, "png", baos);
                baos.flush();

                MultipartFile file = new MockMultipartFile(imgFileName, imgFileName+".png","image/png", baos.toByteArray());

                if(file.isEmpty()) throw new RuntimeException("파일이 null");

                String imgUrl = s3UploadService.saveFile(file, imgFileName);
                //저장 완료된 이미지 경로를 list에 추가
                savedImgList.add(imgUrl);
            }
            pdfDoc.close(); //모두 사용한 PDF 문서는 닫는다.
        }catch (FileNotFoundException e) {

        }catch (IOException e) {

        }
        return savedImgList;
    }

    public TestResultPersonalDto findTestHistoryByTestAndMember (Long testId, Long userId){
        Member member = memberRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 유저 정보가 없습니다."));
        Test test = testRepository.findById(testId).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));
        TestHistory testHistory = testHistoryRepository.findByTest(test).orElseThrow(()-> new RuntimeException("해당 시험 결과 정보가 없습니다."));

        TestPersonalHistory testPersonalHistory = testPersonalHistoryRepository.findByMemberAndTestHistory(member, testHistory).
                orElseThrow(()->new RuntimeException("시험 결과에 해당 유저의 결과가 없습니다."));

        TestResultPersonalDto response = TestResultPersonalDto.of(testPersonalHistory);

        return response;
    }

    public TestHistoryContentUpdateDto updateTestHistoryContent(TestHistoryContentUpdateDto request){
        TestPersonalHistoryContent testPersonalHistoryContent = testPersonalHistoryContentRepository.findById(request.getContentId())
                .orElseThrow(()-> new RuntimeException("해당 문제의 채점 기록이 없습니다."));

        testPersonalHistoryContent.changeResult();
        return request;
    }

    public List<TestHistory> findTestHistoryByClass (Long classId){
        UserClass userClass = classRepository.findById(classId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"class not found"));
        List<TestHistory> testHistoryList = testHistoryRepository.findAllByUserClass(userClass);

        return testHistoryList;
    }

    //가장 최근 시험의 날짜, 점수대별 분포, 평균 정답률, 통과자율, 응시율
    public LatestTestInfoDto getLatestTestInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new RuntimeException("해당 멤버가 없습니다."));

        Optional<List<TestPersonalHistory>> historyList= testPersonalHistoryRepository.findAllByMember(member);
        if(!historyList.isPresent()) return new LatestTestInfoDto();

        List<TestHistory> temp = testHistoryRepository.findLatestTestHitoryByMemberID(member.getId(), PageRequest.of(0,1));
        if(temp.isEmpty()) return new LatestTestInfoDto();
        TestHistory testHistory = temp.get(0);
        int attend = testHistory.getAttendCount();
        int totalmember = testHistory.getTest().getUserClass().getMemberClassList().size();
        int passcount = testHistory.getPassCount();
        int passRate = (int)((double)passcount/totalmember *100);
        int attendRate = (int)((double)attend/totalmember *100);

        ScoreDistribution scoreDistribution = ScoreDistribution.of(testHistory.getTestPersonalHistoryList());

        LatestTestInfoDto response = LatestTestInfoDto.builder().date(testHistory.getTest().getDate()).average(testHistory.getAverage())
                .attendRate(attendRate).passRate(passRate).scoreDistribution(scoreDistribution).build();

        return response;
    }

    // 유저의 오답 조회 후 반환
    public List<TestResultContentDto> getIncorrectById(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new RuntimeException("해당 멤버가 없습니다."));

        List<TestPersonalHistory> testPersonalHistoryList = testPersonalHistoryRepository.findAllByMember(member).orElseThrow(()->new RuntimeException("해당 유저의 지난 결과가 없습니다."));
        List<TestResultContentDto> response = new ArrayList<>();

        for(TestPersonalHistory e : testPersonalHistoryList){
            for(TestPersonalHistoryContent content : e.getTestPersonalHistoryContentList()){
                if(!content.isResult()){
                    response.add(TestResultContentDto.of(content));
                }
            }
        }
        return response;
    }

    public List<TestResultListDto> getTestResultList(Long testId){
        Test test = testRepository.findById(testId).orElseThrow(()-> new RuntimeException("해당 시험이 없습니다."));
        TestHistory testHistory = testHistoryRepository.findByTest(test).orElseThrow(()-> new RuntimeException("해당 시험결과가 없습니다."));

        List<TestResultListDto> response = new ArrayList<>();
        for(TestPersonalHistory e : testHistory.getTestPersonalHistoryList()){
            TestResultListDto testResultListDto = TestResultListDto.builder().url(e.getImage()).hundredScore(e.getHundredScore())
                    .maxScore(e.getMaxScore()).totalScore(e.getScore()).memberId(e.getMember().getId()).username(e.getMember().getUsername()).name(e.getMember().getName())
                    .pass(e.isPass()).build();
            response.add(testResultListDto);
        }

        return response;
    }

}
