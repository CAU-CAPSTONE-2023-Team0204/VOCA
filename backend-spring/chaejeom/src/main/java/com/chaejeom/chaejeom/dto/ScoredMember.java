package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoredMember {
    private Long id;
    private String username;
    private String name;

    public ScoredMember of(Member member) {
        ScoredMember smember = new ScoredMember();
        smember.setId(member.getId());
        smember.setUsername(member.getUsername());
        smember.setName(member.getName());
        return smember;
    }
}
