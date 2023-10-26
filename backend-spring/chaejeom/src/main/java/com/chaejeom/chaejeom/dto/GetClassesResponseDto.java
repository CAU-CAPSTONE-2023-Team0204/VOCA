package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.UserClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetClassesResponseDto {
    // 클래스 목록과 정보를 반환해야 한다.
    private List<UserClass> userClassList;
}
