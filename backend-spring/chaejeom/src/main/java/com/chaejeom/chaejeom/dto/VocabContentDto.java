package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.VocabListContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VocabContentDto {
    private List<VocabListContent> vocabListContents = new ArrayList<>();
}
