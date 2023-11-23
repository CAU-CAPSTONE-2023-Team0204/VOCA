package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.service.VocabService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/vocablist")
@RequiredArgsConstructor
public class VocabController {
    private final VocabService vocabService;

    @Operation(
            summary = "전체 단어장 조회"
    )
    @GetMapping
    public ResponseEntity<VocabListResponseDto> findAllVocabList(){
        return ResponseEntity.ok(vocabService.getVocabLists());
    }

    @Operation(
            summary = "단어장 id로 전체 단어 목록 조회"
    )

    @GetMapping("/{voca_list_id}")
    public ResponseEntity<VocabContentDto> findVocabContent(@PathVariable Long voca_list_id){
        return ResponseEntity.ok(vocabService.getVocabContentByListID(voca_list_id));
    }
    @Operation(
            summary = "단어장 id로 단어장 정보 조회"
    )
    @GetMapping("/info/{voca_list_id}")
    public ResponseEntity<VocabListInfoDto> findVocabListInfo(@PathVariable Long voca_list_id){
        return ResponseEntity.ok(vocabService.getVocabListInfoByClassId(voca_list_id));
    }


    @Operation(
            summary = "특정 단어장에 단어 추가"
    )
    @PostMapping("/{voca_list_id}")
    public ResponseEntity<VocabContentDto> addVocabContent(@PathVariable Long voca_list_id, @RequestBody VocabContentDto vocabContentDto){
        return ResponseEntity.ok(vocabService.addVocabListContent(voca_list_id, vocabContentDto));
    }

    @Operation(
            summary = "특정 단어 삭제"
    )
    @DeleteMapping("/{voca_content_id}")
    public ResponseEntity<VocabContentDto> deleteVocabContent(@PathVariable Long voca_content_id){
        return ResponseEntity.ok(vocabService.deleteVocabListContent(voca_content_id));
    }

    @Operation(
            summary = "클래스에 단어장 추가"
    )
    @PostMapping("/{voca_list_id}/{class_id}")
    public ResponseEntity<ClassVocabListResponseDto> addClassVocabList(@PathVariable("voca_list_id")Long voca_list_id, @PathVariable("class_id")Long class_id){
        return ResponseEntity.ok(vocabService.addClassVocabList(voca_list_id, class_id));
    }

    @Operation(
            summary = "클래스에 설정된 단어장 삭제"
    )
    @DeleteMapping("/{voca_list_id}/{class_id}")
    public ResponseEntity<ClassVocabListResponseDto> deleteClassVocabList(@PathVariable("voca_list_id")Long voca_list_id, @PathVariable("class_id")Long class_id){
        return ResponseEntity.ok(vocabService.deleteClassVocabList(voca_list_id, class_id));
    }
    @Operation(
            summary = "특정 클래스의 단어장 목록 조회"
    )
    @GetMapping("/class/{class_id}")
    public ResponseEntity<VocabListResponseDto> findClassVocabList(@PathVariable Long class_id){
        return ResponseEntity.ok(vocabService.getVocabListsByClassId(class_id));
    }

    @Operation(
            summary = "단어장 직접 생성"
    )
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VocabListResponseDto> createVocabList(@RequestPart(value ="file") MultipartFile file,
                                                                @RequestPart(value= "request") VocabListRequestDto vocabListRequestDto) throws IOException {
        return ResponseEntity.ok(vocabService.createVocabList(vocabListRequestDto, file));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRunTimeException (RuntimeException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
