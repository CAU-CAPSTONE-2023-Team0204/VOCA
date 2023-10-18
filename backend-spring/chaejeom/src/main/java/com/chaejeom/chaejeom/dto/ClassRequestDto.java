package com.chaejeom.chaejeom.dto;


import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.UserClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRequestDto {

    private String name;
    private Member teacher;
    private String teacher_id;

    public UserClass toClass() {
        return UserClass.builder()
                .name(name)
                .build();
    }

}