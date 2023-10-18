package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.UserClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponseDto {
    private String name;

    public static ClassResponseDto of(UserClass myClass) {
        return new ClassResponseDto(myClass.getName());
    }

}
