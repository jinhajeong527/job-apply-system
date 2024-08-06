package com.wanted.pre_onboarding.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Address {

    private String country;
    private String city;
    private String district;
    private String street;
    private String building;
    private String zipcode;
    @Builder
    public Address(String country, String city, String district,
                   String street, String building, String zipcode) {
        this.country = country;
        this.city = city;
        this.district = district;
        this.street = street;
        this.building = building;
        this.zipcode = zipcode;
    }
}
