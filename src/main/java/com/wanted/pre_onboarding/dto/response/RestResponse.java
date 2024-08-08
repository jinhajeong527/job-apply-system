package com.wanted.pre_onboarding.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
    String message;
    T data;

    public RestResponse(String message) {
        this.message = message;
    }

    public RestResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
