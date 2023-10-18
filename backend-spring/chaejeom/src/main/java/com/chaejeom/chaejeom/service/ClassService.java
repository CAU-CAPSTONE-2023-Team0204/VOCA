package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.dto.ClassRequestDto;
import com.chaejeom.chaejeom.dto.ClassResponseDto;
import com.chaejeom.chaejeom.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;

    //클래스 정보를 담은 requestDto 를 통해 클래스를 db에 저장하고, responseDto에 담아 클라이언트에 반환//
    public ClassResponseDto add(ClassRequestDto request){
        return ClassResponseDto.of(classRepository.save(request.toClass()));
    }
}
