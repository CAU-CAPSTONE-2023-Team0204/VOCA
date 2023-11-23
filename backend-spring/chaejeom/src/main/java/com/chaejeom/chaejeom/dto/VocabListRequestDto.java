package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.VocabListContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VocabListRequestDto {
    private String name;
    private String description;
    private List<VocabListContent> contents;

}
