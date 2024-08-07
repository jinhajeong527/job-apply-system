package com.wanted.pre_onboarding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class JobPostingRequest {

    @NotNull(message = "Company ID is required.")
    private Long companyId;

    @NotBlank(message = "Position is required.")
    private String position;

    @Positive(message = "Reward must be greater than zero.")
    private int reward;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotEmpty(message = "At least one skill must be specified.")
    private Set<String> usedSkills;

    public JobPostingRequest(Long companyId, String position, int reward, String description, Set<String> usedSkills) {
        this.companyId = companyId;
        this.position = position;
        this.reward = reward;
        this.description = description;
        this.usedSkills = usedSkills;
    }
}
