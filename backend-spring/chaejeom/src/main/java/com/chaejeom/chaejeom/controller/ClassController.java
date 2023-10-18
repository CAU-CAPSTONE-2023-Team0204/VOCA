package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.ClassRequestDto;
import com.chaejeom.chaejeom.dto.ClassResponseDto;
import com.chaejeom.chaejeom.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classes")
public class ClassController {
    private final ClassService classService;
    @PostMapping("/teacher/classes")
    public ResponseEntity<ClassResponseDto> addClass(@RequestBody ClassRequestDto classRequestDto) {
        return ResponseEntity.ok(classService.add(classRequestDto));
    }

}
