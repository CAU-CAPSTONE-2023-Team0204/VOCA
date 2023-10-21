package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.MemberClass;
import com.chaejeom.chaejeom.domain.UserClass;
import com.chaejeom.chaejeom.dto.ClassRequestDto;
import com.chaejeom.chaejeom.dto.ClassResponseDto;
import com.chaejeom.chaejeom.repository.ClassRepository;
import com.chaejeom.chaejeom.repository.MemberClassRepository;
import com.chaejeom.chaejeom.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final MemberRepository memberRepository;
    private final MemberClassRepository memberClassRepository;



    // 처음 클래스를 생성하는 Service. 생성하고자 하는 클래스의 이름과 학생 id 명단이 request body
    //클래스 정보를 담은 requestDto 를 통해 클래스를 db에 저장하고, responseDto에 담아 클라이언트에 반환//
    // request dto 에는 클래스의 이름과 학생들의 id 명단이 전달된다.
    // userClass 는 새로이 생성되고, 학생은 member 테이블에 존재해야함.
    // memberClass 도 학생 수만큼 새로이 생성됨
    // 생성된 memberClass는 userClass와 member 객체의 memberClassList 에도 추가되어야함.
    // 테스트 필요하다.
    public ClassResponseDto add(ClassRequestDto request){
        // name 에 따라 UserClass 생성 후 저장
        UserClass userclass = UserClass.builder().name(request.getName()).build();
        ClassResponseDto classResponseDto = ClassResponseDto.of(classRepository.save(userclass));


        // 학생들의 id 명단만큼 memberClass 생성, id로 member 검색 후 memberClass에 넣기.
        // 이후 userClass 도 넣고 DB에 memberClass 를 저장한다.
        for(String member : request.getMembers()){
            MemberClass memberClass = new MemberClass();
            Member member1= memberRepository.findByUsername(member)
                    .orElseThrow(() -> new UsernameNotFoundException(member + " -> 데이터베이스에서 찾을 수 없습니다."));
            memberClass.setUserClass(userclass);
            memberClass.setMember(member1);

            memberClassRepository.save(memberClass);
        }

        // 학생 수만큼 memberClass 생성.

        return classResponseDto;
    }
}
