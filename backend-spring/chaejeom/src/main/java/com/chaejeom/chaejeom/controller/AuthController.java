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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {
        TokenDto tokenDto = authService.login(memberRequestDto);
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                //.sameSite("None")
                // secure 를 true로 설정하면 https 에서만 쿠키가 전달됨 추후 적용 후 수정필요.
                .secure(false)
                .path("/")
                .maxAge( 60 * 60 * 24 * 7)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(tokenDto);
    }

    @Operation(
            summary = "엑세스 토큰 만료시 토큰 재발급"
    )
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@CookieValue("refreshToken") String refreshtoken, @RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = authService.reissue(tokenRequestDto, refreshtoken);
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                //.sameSite("None")
                // secure 를 true로 설정하면 https 에서만 쿠키가 전달됨 추후 적용 후 수정필요.
                .secure(false)
                .path("/")
                .maxAge( 60 * 60 * 24 * 7)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(tokenDto);
    }
    @GetMapping("/logout") // 로그아웃 시 토큰 처리 필요하다.
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "logout";
    }
}