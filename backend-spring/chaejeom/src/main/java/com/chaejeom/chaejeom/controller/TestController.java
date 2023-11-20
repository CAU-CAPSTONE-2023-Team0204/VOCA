package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.service.TestService;
import com.chaejeom.chaejeom.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    // 시험 생성 3가지 모드
    @Operation(
            summary = "자동 시험 출제"
    )
    @PostMapping("/auto")
    public ResponseEntity<AutoTestResponseDto> createAutoTest(@RequestBody AutoTestRequestDto request) {
        return ResponseEntity.ok(testService.createAutoTest(request));
    }

    @Operation(
            summary = "수동 시험 출제"
    )
    @PostMapping("/manual")
    public ResponseEntity<ManualTestResponseDto> createManualTest(@RequestBody ManualTestRequestDto request) {
        return ResponseEntity.ok(testService.createManualTest(request));
    }

    // 내 시험 조회
    @Operation(
            summary = "접속중인 유저 시험 모두 조회"
    )
    @GetMapping("/me")
    public ResponseEntity<TestListResponseDto> findTestByUserId() {
        return ResponseEntity.ok(testService.findTestByUserId(SecurityUtil.getCurrentMemberId()));
    }

    @Operation(
            summary = "클래스의 시험 모두 조회"
    )
    @GetMapping("/{class_id}")
    public ResponseEntity<TestListResponseDto> findTestByClassId(@PathVariable Long class_id) {
        return ResponseEntity.ok(testService.findTestByClassId(class_id));
    }

    @Operation(
            summary = "접속중인 유저의 앞으로 예정된 시험 조회"
    )
    @GetMapping("/todo/me")
    public ResponseEntity<TestListResponseDto> findTodoTestByUserId() {
        return ResponseEntity.ok(testService.findTodoTestByUserId(SecurityUtil.getCurrentMemberId()));
    }

    @Operation(
            summary = "클래스의 앞으로 예정된 시험 모두 조회"
    )
    @GetMapping("/todo/{class_id}")
    public ResponseEntity<TestListResponseDto> findTodoTestByClassId(@PathVariable Long class_id) {
        return ResponseEntity.ok(testService.findTodoTestByClassId(class_id));
    }

    @Operation(
            summary = "시험 id 로 시험 내용 조회"
    )
    @GetMapping("/start/{test_id}")
    public ResponseEntity<TestResponseDto> findTestByTestId(@PathVariable Long test_id) {
        return ResponseEntity.ok(testService.findTestByTestId(test_id));
    }
    //시험 결과 제출 및 채점 실행 //

    ////// api test/////
    @Operation(
            summary = "시험 제출 테스트중"
    )
    @PostMapping(value = "/apitest/start/{test_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ScoringRequestDto> doScoringTest(@RequestPart(value = "file") MultipartFile multipartFile,
                                                           @RequestPart(value = "request") TestResultRequestDto request) throws IOException {
        return ResponseEntity.ok(testService.scoringTest(multipartFile, request));
    }
    @Operation(
            summary = "시험 결과 받기 테스트중"
    )
    @PostMapping("/apitest/result/{test_id}")
    public ResponseEntity<TestResultResponseDto> getResultTest(@RequestBody TestResultResponseDto request){
        return ResponseEntity.ok(testService.getResultTest(request));
    }

    @Operation(
            summary = "시험 답안 파일 제출"
    )
    @PostMapping(value = "/start/{test_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TestResultResponseDto> doScoring(@RequestPart(value = "file") MultipartFile multipartFile,
                                                           @RequestPart(value = "request") TestResultRequestDto request) throws IOException {
        return ResponseEntity.ok(testService.scoring(multipartFile, request));
    }
    // 채점 결과 받기//


    // 내 시험 결과 조회

    // 클래스 시험 결과 조회
}
