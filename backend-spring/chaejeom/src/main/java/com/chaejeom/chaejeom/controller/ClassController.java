package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.*;
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
            summary = "접속한 회원의 모든 클래스 정보 반환"
    )
    @GetMapping("/classes/me")
    public ResponseEntity<GetClassesResponseDto> findClassesByMe(){
        return ResponseEntity.ok(classService.getClassesInfo());
    }

    @Operation(
            summary = "클래스 id로 클래스에 속한 사용자 정보 반환"
    )
    @GetMapping("/classes/user/{class_id}")
    public ResponseEntity<GetStudentResponseDto> findStudentByClass(@PathVariable Long class_id){
        return ResponseEntity.ok(classService.getStudentInfoById(class_id));
    }

    @Operation(
            summary = "해당 유저를 클래스에서 삭제"
    )
    @DeleteMapping("/classes/{class_id}/{user_id}")
    public ResponseEntity<MemberResponseDto> deleteMemberById(@PathVariable("class_id") Long class_id ,@PathVariable("user_id") Long user_id){
        return ResponseEntity.ok(classService.deleteMemberInClass(class_id,user_id));
    }

}
