package com.wanted.pre_onboarding.domain;

import com.wanted.pre_onboarding.domain.base.BaseTimeEntity;
import com.wanted.pre_onboarding.domain.vo.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    private String name;

    private String industryName;

    @Embedded
    private Address address;
}
