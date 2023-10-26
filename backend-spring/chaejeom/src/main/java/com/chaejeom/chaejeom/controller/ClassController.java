package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.ClassRequestDto;
import com.chaejeom.chaejeom.dto.ClassResponseDto;
import com.chaejeom.chaejeom.dto.GetClassesResponseDto;
import com.chaejeom.chaejeom.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClassController {

    private final ClassService classService;
    @Operation(
            summary = "클래스 생성", description = "클래스 이름과 학생 id 명단으로 클래스를 생성한다."
    )
    @PostMapping("/teacher/classes")
    public ResponseEntity<ClassResponseDto> createClass(@RequestBody ClassRequestDto classRequestDto) {
        return ResponseEntity.ok(classService.create(classRequestDto));
    }

    @Operation(
            summary = "접속한 회원의 클래스 목록 반환"
    )
    @GetMapping("/classes/me")
    public ResponseEntity<GetClassesResponseDto> findClassesByMe(){
        return ResponseEntity.ok(classService.getClassesInfo());
    }

}
