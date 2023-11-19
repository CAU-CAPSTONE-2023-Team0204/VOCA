package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.*;
import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    private final S3UploadService s3UploadService;

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
    public TestResponseDto findTestByTestId(long id){
        Test test = testRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));

        return TestResponseDto.of(test);
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

// 클라이언트에서 시험 id 와 시험지 파일을 받아 저장하고, 파일과 시험문제 정보를 반환하는 테스트함수.
    public ScoringRequestDto scoringTest(MultipartFile multipartFile, TestResultRequestDto request) throws IOException {
        Test test = testRepository.findById(request.getTestId()).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));
        // 시험지 pdf파일 저장
        String url = s3UploadService.saveFile(multipartFile, "test_content_"+test.getId().toString()+"_");

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

    // 클라이언트에서 시험id와 시험지 파일을 받아 클라우드에 저장하고, 파일과 시험 정보를 장고 서버에 전달. 채점 완료된 정보를 json 형태로 반환받아 객체에 저장후 반환.
    public TestResultResponseDto scoring(MultipartFile multipartFile, TestResultRequestDto request) throws IOException {
        Test test = testRepository.findById(request.getTestId()).orElseThrow(()-> new RuntimeException("해당 시험 정보가 없습니다."));
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

        //ai 파트와 협의 후 결정될 사안
        final String djangoURL = "http://localhost:8000/score";

        RestTemplate restTemplate = new RestTemplate();
        TestResultResponseDto response= restTemplate.postForObject(djangoURL, scoringRequestDto, TestResultResponseDto.class);

        ////////////////////////
        // 시험 결과 데이터 처리 //

        test.endTest();

        return response;
    }

    // pdf to image List
    private List<String> conversionPdf2Img(InputStream is, String uniqueId) {
        List<String> savedImgList = new ArrayList<>(); //저장된 이미지 경로 list
        try {
            PDDocument pdfDoc = PDDocument.load(is); //Document 생성
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);

            // PDF페이지 루프
            for (int i=0; i<pdfDoc.getPages().getCount(); i++) {
                String imgFileName = uniqueId + "-" + i ;

                //DPI 설정
                BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 72, ImageType.GRAY);

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
}
