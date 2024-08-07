package com.wanted.pre_onboarding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationResult {
    private String field;
    private String rejectedValue;
    private String message;
}
