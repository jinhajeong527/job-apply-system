package com.wanted.pre_onboarding.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApply {
    private Long userId;
    private Long jobPostId;

    @Builder
    public JobApply(Long userId, Long jobPostId) {
        this.userId = userId;
        this.jobPostId = jobPostId;
    }
}
