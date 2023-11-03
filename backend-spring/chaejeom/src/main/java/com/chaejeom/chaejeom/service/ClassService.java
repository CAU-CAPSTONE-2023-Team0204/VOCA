package com.chaejeom.chaejeom.service;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.MemberClass;
import com.chaejeom.chaejeom.domain.UserClass;
import com.chaejeom.chaejeom.dto.*;
import com.chaejeom.chaejeom.repository.ClassRepository;
import com.chaejeom.chaejeom.repository.MemberClassRepository;
import com.chaejeom.chaejeom.repository.MemberRepository;
import com.chaejeom.chaejeom.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ClassResponseDto create(ClassRequestDto request){
        // name 에 따라 UserClass 생성 후 저장
        UserClass userclass = UserClass.builder().name(request.getName()).build();
        ClassResponseDto classResponseDto = ClassResponseDto.of(classRepository.save(userclass));

        // 학생들의 id 명단만큼 memberClass 생성, id로 member 검색 후 memberClass에 넣기.
        // 이후 userClass 도 넣고 DB에 memberClass 를 저장한다.
        for(String username : request.getMembers()){
            MemberClass memberClass = new MemberClass();
            Member member= memberRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));

            memberClass.addClass(userclass);
            memberClass.addMember(member);
            memberClassRepository.save(memberClass);
        }

        return classResponseDto;
    }
    public GetClassesResponseDto getClassesInfo(){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()->new RuntimeException("로그인 정보가 없습니다."));
        List<MemberClass> memberClassList = member.getMemberClassList();
        List<UserClass> userClassList = new ArrayList<>();

        for(int i = 0; i<memberClassList.size();i++){
            userClassList.add(memberClassList.get(i).getUserClass());
        }
        GetClassesResponseDto getClassesResponseDto = new GetClassesResponseDto(userClassList);
        return getClassesResponseDto;
    }

    public GetStudentResponseDto getStudentInfoById(Long id){
        UserClass userClass = classRepository.findById(id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));
        List<MemberClass> memberClassList = userClass.getMemberClassList();
        List<Member> memberList = new ArrayList<>();

        for(MemberClass memberClass : memberClassList){
            memberList.add(memberClass.getMember());
        }
        GetStudentResponseDto getStudentResponseDto = new GetStudentResponseDto(memberList);
        return getStudentResponseDto;
    }

    public MemberResponseDto deleteMemberInClass(Long class_id, Long user_id){
        UserClass userClass = classRepository.findById(class_id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));
        Member member = memberRepository.findById(user_id).orElseThrow(()-> new RuntimeException("유저 정보가 없습니다."));
        MemberClass memberClass = memberClassRepository.findByMemberAndUserClass(member, userClass).orElseThrow(()-> new RuntimeException("해당 클래스에 해당 유저 정보가 없습니다."));

        member.getMemberClassList().remove(memberClass);
        userClass.getMemberClassList().remove(memberClass);

        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);

        memberClassRepository.delete(memberClass);
        return memberResponseDto;
    }

    public GetClassesResponseDto getAllClass(){
        List<UserClass> userClassList = classRepository.findAll();
        GetClassesResponseDto getClassesResponseDto = new GetClassesResponseDto(userClassList);

        return getClassesResponseDto;
    }

    public GetOneClassResponseDto getOneClass(Long class_id){
        UserClass userClass = classRepository.findById(class_id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));
        List<MemberClass> memberClassList = userClass.getMemberClassList();
        List<Member> memberList = new ArrayList<>();

        for(MemberClass memberClass : memberClassList){
            memberList.add(memberClass.getMember());
        }
        GetOneClassResponseDto getOneClassResponseDto = new GetOneClassResponseDto(userClass.getId(), userClass.getName(), memberList);

        return getOneClassResponseDto;
    }

    public MemberResponseDto addStudentById(Long class_id, Long user_id){
        UserClass userClass = classRepository.findById(class_id).orElseThrow(()-> new RuntimeException("클래스 정보가 없습니다."));
        Member member = memberRepository.findById(user_id).orElseThrow(()-> new RuntimeException("유저 정보가 없습니다."));
        if(memberClassRepository.existsByMemberAndUserClass(member, userClass)) throw new RuntimeException("이미 유저가 해당 클래스에 존재합니다.");

        MemberClass memberClass = new MemberClass();

        memberClass.addMember(member);
        memberClass.addClass(userClass);
        memberClassRepository.save(memberClass);
        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);

        return memberResponseDto;
    }
}
