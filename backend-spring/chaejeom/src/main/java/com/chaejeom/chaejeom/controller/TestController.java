package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.AutoTestRequestDto;
import com.chaejeom.chaejeom.dto.AutoTestResponseDto;
import com.chaejeom.chaejeom.dto.ManualTestRequestDto;
import com.chaejeom.chaejeom.dto.ManualTestResponseDto;
import com.chaejeom.chaejeom.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    // 시험 생성 3가지 모드
    @PostMapping("/auto")
    public ResponseEntity<AutoTestResponseDto> createAutoTest(@RequestBody AutoTestRequestDto request){
        return ResponseEntity.ok(testService.createAutoTest(request));
    }

    @PostMapping("/manual")
    public ResponseEntity<ManualTestResponseDto> createManualTest(@RequestBody ManualTestRequestDto request){
        return ResponseEntity.ok(testService.createManualTest(request));
    }
    // 내 예정 시험 조회
    
    // 내 시험 결과 조회
}
