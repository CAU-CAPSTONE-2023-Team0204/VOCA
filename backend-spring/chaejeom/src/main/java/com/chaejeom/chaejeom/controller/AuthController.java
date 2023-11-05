package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.MemberRequestDto;
import com.chaejeom.chaejeom.dto.MemberResponseDto;
import com.chaejeom.chaejeom.dto.TokenDto;
import com.chaejeom.chaejeom.dto.TokenRequestDto;
import com.chaejeom.chaejeom.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {

        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @Operation(
            summary = "엑세스 토큰 만료시 토큰 재발급"
    )
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
    @GetMapping("/logout") // 로그아웃 시 토큰 처리 필요하다.
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "logout";
    }
}