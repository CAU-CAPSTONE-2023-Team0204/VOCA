package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.ClassVocabList;
import com.chaejeom.chaejeom.domain.UserClass;
import com.chaejeom.chaejeom.domain.VocabList;
import com.chaejeom.chaejeom.domain.VocabListContent;
import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.repository.ClassRepository;
import com.chaejeom.chaejeom.repository.ClassVocabListRepository;
import com.chaejeom.chaejeom.repository.VocabListContentRepository;
import com.chaejeom.chaejeom.repository.VocabListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VocabService {
    private final VocabListRepository vocabListRepository;
    private final VocabListContentRepository vocabListContentRepository;
    private final ClassVocabListRepository classVocabListRepository;
    private final ClassRepository classRepository;
    private final S3UploadService s3UploadService;


    // db에 존재하는 모든 단어장 조회
    public VocabListResponseDto getVocabLists(){
        List<VocabList> vocabLists = vocabListRepository.findAll();
        VocabListResponseDto vocabListResponseDto = new VocabListResponseDto(vocabLists);

        return vocabListResponseDto;
    }

    // 클래스 id 로 해당 클래스 단어장 목록 조회
    public VocabListResponseDto getVocabListsByClassId(Long class_id){
        UserClass userClass = classRepository.findById(class_id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));
        List<ClassVocabList> classVocabLists= userClass.getClassVocabLists();
        List<VocabList> vocabLists = new ArrayList<>();
        for(ClassVocabList classVocabList:classVocabLists){
            vocabLists.add(classVocabList.getVocabList());
        }
       VocabListResponseDto vocabListResponseDto = new VocabListResponseDto(vocabLists);

        return vocabListResponseDto;
    }

    public VocabListInfoDto getVocabListInfoByClassId(Long voca_list_id){
        VocabList vocabList = vocabListRepository.findById(voca_list_id).orElseThrow(()-> new RuntimeException("단어장 정보가 없습니다."));
        VocabListInfoDto response = VocabListInfoDto.of(vocabList);
        return response;
    }

    // 단어장 id 로 해당 단어장 단어 목록 조회
    public VocabContentDto getVocabContentByListID(Long voca_list_id){
        VocabList vocabList = vocabListRepository.findById(voca_list_id).orElseThrow(()-> new RuntimeException("단어장 정보가 없습니다."));
        VocabContentDto vocabContentDto = new VocabContentDto(vocabList.getVocabListContents());

        return vocabContentDto;
    }

    // 클래스 - 단어장 연결 추가
    public ClassVocabListResponseDto addClassVocabList(Long voca_list_id, Long class_id){

        VocabList vocabList = vocabListRepository.findById(voca_list_id).orElseThrow(()-> new RuntimeException("단어장 정보가 없습니다."));
        UserClass userClass =classRepository.findById(class_id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));

        if(classVocabListRepository.existsByVocabListAndUserClass(vocabList,userClass)){
            throw new RuntimeException("해당 단어장이 이미 클래스에 등록되어 있습니다.");
        }

        ClassVocabList classVocabList = new ClassVocabList();
        classVocabList.addUserClass(userClass);
        classVocabList.addVocabList(vocabList);

        classVocabListRepository.save(classVocabList);
        ClassVocabListResponseDto classVocabListResponseDto = new ClassVocabListResponseDto(classVocabList);
        return classVocabListResponseDto;
    }

    // 클래스 - 단어장 연결 삭제
    public ClassVocabListResponseDto deleteClassVocabList(Long voca_list_id, Long class_id){
        VocabList vocabList = vocabListRepository.findById(voca_list_id).orElseThrow(()-> new RuntimeException("단어장 정보가 없습니다."));
        UserClass userClass =classRepository.findById(class_id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));

        ClassVocabList classVocabList = classVocabListRepository.findByVocabListAndUserClass(vocabList, userClass).orElseThrow(()-> new RuntimeException("해당 클래스에 해당 단어장 정보가 없습니다."));
        userClass.getClassVocabLists().remove(classVocabList);
        ClassVocabListResponseDto classVocabListResponseDto = new ClassVocabListResponseDto(classVocabList);

        classVocabListRepository.delete(classVocabList);

        return classVocabListResponseDto;
    }

    // 단어장 수동 생성
    public VocabListResponseDto createVocabList(VocabListRequestDto vocabListRequestDto, MultipartFile file) throws IOException {
        String url = s3UploadService.saveFile(file, vocabListRequestDto.getName());

        VocabList vocabList = VocabList.builder().name(vocabListRequestDto.getName()).description(vocabListRequestDto.getDescription())
                .image(url).vocabListContents(new ArrayList<>()).build();

        for(VocabListContent vocabListContent : vocabListRequestDto.getContents()){
            vocabListContent.setVocabList(vocabList); // 외래키 설정
            vocabListContentRepository.save(vocabListContent); // 단어 db에 저장
            vocabList.getVocabListContents().add(vocabListContent);
        }
        vocabListRepository.save(vocabList);
        VocabListResponseDto vocabListResponseDto = new VocabListResponseDto(vocabList);

        return vocabListResponseDto;
    }

    // 단어장에 단어 직접 추가
    public VocabContentDto addVocabListContent(Long voca_list_id, VocabContentDto vocabContentDto){
        VocabList vocabList = vocabListRepository.findById(voca_list_id).orElseThrow(()-> new RuntimeException("단어장 정보가 없습니다."));
        VocabContentDto response = new VocabContentDto();
        for (VocabListContent vocabListContent : vocabContentDto.getVocabListContents()){
            vocabListContent.addContent(vocabList);

            vocabListContentRepository.save(vocabListContent);
            response.getVocabListContents().add(vocabListContent);
        }

        return response;
    }

    // 특정 단어 삭제하기
    public VocabContentDto deleteVocabListContent(Long voca_content_id){
        VocabListContent vocabListContent = vocabListContentRepository.findById(voca_content_id).orElseThrow(()-> new RuntimeException("단어 정보가 없습니다."));
        VocabContentDto response = new VocabContentDto();

        vocabListContent.getVocabList().getVocabListContents().remove(vocabListContent);
        vocabListContentRepository.delete(vocabListContent);

        return response;
    }
}
