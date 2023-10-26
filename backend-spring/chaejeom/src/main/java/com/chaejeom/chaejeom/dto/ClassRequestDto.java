package com.chaejeom.chaejeom.dto;


import com.chaejeom.chaejeom.domain.UserClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRequestDto {

    private String name;
    private List<String> members;

    //private String teacher_id;

    public UserClass toClass() {
        return UserClass.builder()
                .name(name)
                .build();
    }

}