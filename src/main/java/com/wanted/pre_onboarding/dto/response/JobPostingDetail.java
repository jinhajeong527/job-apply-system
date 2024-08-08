package com.wanted.pre_onboarding.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class JobPostingDetail {

    private Long postId;
    private String companyName;
    private String country;
    private String region;
    private String position;
    private Integer reward;
    private Set<String> usedSkills;
    private String description;
    private List<Long> others;

    @Builder
    public JobPostingDetail(Long postId, String companyName, String country, String region,
                            String position, Integer reward, Set<String> usedSkills,
                            String description, List<Long> others) {
        this.postId = postId;
        this.companyName = companyName;
        this.country = country;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.usedSkills = usedSkills;
        this.description = description;
        this.others = others;
    }
}
