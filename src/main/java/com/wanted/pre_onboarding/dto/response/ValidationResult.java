package com.wanted.pre_onboarding.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResult {
    private String field;
    private String rejectedValue;
    private String message;
    @Builder
    public ValidationResult(String field, String rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
