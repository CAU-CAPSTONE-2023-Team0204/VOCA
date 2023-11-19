package com.chaejeom.chaejeom.dto;

import com.chaejeom.chaejeom.domain.Member;
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
            ScoredMember scoredMember = new ScoredMember();
            this.memberList.add(scoredMember.of(member));
        }
    }
}
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class ScoredMember{
        Long id;
        String username;
        String name;
        public ScoredMember of(Member member){
            ScoredMember smember = new ScoredMember();
            smember.setId(member.getId());
            smember.setUsername(member.getUsername());
            smember.setName(member.getName());
            return smember;
        }
    }
