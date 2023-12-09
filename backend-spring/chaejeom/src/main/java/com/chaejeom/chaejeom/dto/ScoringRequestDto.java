package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
import com.chaejeom.chaejeom.domain.Role;
import com.chaejeom.chaejeom.domain.TestContent;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoringRequestDto {
    private Long testID;
    private Long classID;
    private List<TestContent> testContentList = new ArrayList<>();
    private List<ScoredMember> memberList = new ArrayList<>();
    private List<String> file = new ArrayList<>();

    public void setMemberList(List<Member> list) {
        for (Member member : list) {
            if (member.getRole() == Role.ROLE_STUDENT) {
                ScoredMember scoredMember = new ScoredMember();
                this.memberList.add(scoredMember.of(member));
            }
        }
    }
}
