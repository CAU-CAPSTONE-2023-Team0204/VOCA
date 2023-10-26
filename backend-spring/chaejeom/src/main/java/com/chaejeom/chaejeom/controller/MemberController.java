package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.MemberResponseDto;
import com.chaejeom.chaejeom.service.MemberService;
import com.chaejeom.chaejeom.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    /*@GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
    }*/

    @GetMapping("/{username}")
    public ResponseEntity<MemberResponseDto> findMemberInfoByUsername(@PathVariable String username) {
        return ResponseEntity.ok(memberService.findMemberInfoByUsername(username));

    }
}