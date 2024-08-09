package com.wanted.pre_onboarding.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplyResponse {
    private String username;
    private String email;
    private String companyName;
    private String position;
    private LocalDateTime applyTime;

    @Builder
    public ApplyResponse(String username, String email, String companyName, String position, LocalDateTime applyTime) {
        this.username = username;
        this.email = email;
        this.companyName = companyName;
        this.position = position;
        this.applyTime = applyTime;
    }
}
