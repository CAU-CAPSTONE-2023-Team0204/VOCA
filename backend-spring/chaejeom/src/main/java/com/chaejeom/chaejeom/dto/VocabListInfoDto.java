package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.VocabCategory;
import com.chaejeom.chaejeom.domain.VocabList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VocabListInfoDto {
    private String name;
    private String img;
    private String description;
    private VocabCategory category;

    public static VocabListInfoDto of(VocabList vocabList){
        return VocabListInfoDto.builder()
                .name(vocabList.getName())
                .category(vocabList.getCategory() == null ? null : vocabList.getCategory())
                .img(vocabList.getImage())
                .description(vocabList.getDescription()).build();
    }
}
