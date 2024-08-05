package com.wanted.pre_onboarding.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Address {

    private String country;
    private String city;
    private String street;
    private String zipcode;
}
