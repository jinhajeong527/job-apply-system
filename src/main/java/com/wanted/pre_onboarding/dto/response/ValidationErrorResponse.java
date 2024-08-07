package com.wanted.pre_onboarding.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ValidationErrorResponse {
    List<ValidationResult> results;

    public ValidationErrorResponse(List<ValidationResult> results) {
        this.results = results;
    }
}
