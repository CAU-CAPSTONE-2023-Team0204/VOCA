package com.chaejeom.chaejeom.dto;
import com.chaejeom.chaejeom.domain.Role;
import com.chaejeom.chaejeom.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


// 회원가입 요청 할때 이 정보들이 필요하다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String username;
    private String email;
    private String password;

    private String name;
    private Role role;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .role(role)
                .build();
    }

    // username 이 id password 가 비밀번호
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}