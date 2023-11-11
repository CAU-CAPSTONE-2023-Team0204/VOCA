package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.service.TestService;
import com.chaejeom.chaejeom.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AutoTestResponseDto> createAutoTest(@RequestBody AutoTestRequestDto request){
        return ResponseEntity.ok(testService.createAutoTest(request));
    }

    @Operation(
            summary = "수동 시험 출제"
    )
    @PostMapping("/manual")
    public ResponseEntity<ManualTestResponseDto> createManualTest(@RequestBody ManualTestRequestDto request){
        return ResponseEntity.ok(testService.createManualTest(request));
    }
    // 내 시험 조회
    @Operation(
            summary = "접속중인 유저 시험 모두 조회"
    )
    @GetMapping("/me")
    public ResponseEntity<TestListResponseDto> findTestByUserId(){
        return ResponseEntity.ok(testService.findTestByUserId(SecurityUtil.getCurrentMemberId()));
    }
    @Operation(
            summary = "클래스의 시험 모두 조회"
    )
    @GetMapping("/{class_id}")
    public ResponseEntity<TestListResponseDto> findTestByClassId(@PathVariable Long class_id){
        return ResponseEntity.ok(testService.findTestByClassId(class_id));
    }

    @Operation(
            summary = "접속중인 유저의 앞으로 예정된 시험 조회"
    )
    @GetMapping("/todo/me")
    public ResponseEntity<TestListResponseDto> findTodoTestByUserId(){
        return ResponseEntity.ok(testService.findTodoTestByUserId(SecurityUtil.getCurrentMemberId()));
    }
    @Operation(
            summary = "클래스의 앞으로 예정된 시험 모두 조회"
    )
    @GetMapping("/todo/{class_id}")
    public ResponseEntity<TestListResponseDto> findTodoTestByClassId(@PathVariable Long class_id){
        return ResponseEntity.ok(testService.findTodoTestByClassId(class_id));
    }



    // 내 시험 결과 조회
}
