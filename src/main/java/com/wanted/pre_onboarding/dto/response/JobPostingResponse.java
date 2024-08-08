package com.wanted.pre_onboarding.dto.response;

import com.wanted.pre_onboarding.domain.JobPosting;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class JobPostingResponse {

    private Long registeredId;
    private String position;
    private String description;
    private int reward;
    private Set<String> usedSkills;
    private LocalDateTime created;
    private LocalDateTime lastModified;

    @Builder
    public JobPostingResponse(Long registeredId, String position, String description,
                              int reward, LocalDateTime created, LocalDateTime lastModified, Set<String> usedSkills) {
        this.registeredId = registeredId;
        this.position = position;
        this.description = description;
        this.reward = reward;
        this.created = created;
        this.lastModified = lastModified;
        this.usedSkills = usedSkills;
    }

    public static JobPostingResponse fromEntity(JobPosting jobPosting) {
        return JobPostingResponse.builder()
                .registeredId(jobPosting.getId())
                .position(jobPosting.getPosition())
                .description(jobPosting.getDescription())
                .reward(jobPosting.getReward())
                .created(jobPosting.getCreated())
                .lastModified(jobPosting.getLastModified())
                .usedSkills(jobPosting.getUsedSkills())
                .build();
    }

}
