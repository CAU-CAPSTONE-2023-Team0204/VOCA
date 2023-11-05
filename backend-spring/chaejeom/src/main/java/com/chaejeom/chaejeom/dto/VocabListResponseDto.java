package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.VocabList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VocabListResponseDto {

    private List<VocabList> vocabLists;

    public VocabListResponseDto(VocabList vocabList){
        vocabLists = new ArrayList<>();
        vocabLists.add(vocabList);
    }
}
