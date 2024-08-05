package com.wanted.pre_onboarding.domain;

import com.wanted.pre_onboarding.domain.base.BaseTimeEntity;
import com.wanted.pre_onboarding.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Application extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus status;

    // 지원서 열람 여부
    private boolean isViewed;

}
