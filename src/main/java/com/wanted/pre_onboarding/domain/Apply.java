package com.wanted.pre_onboarding.domain;

import com.wanted.pre_onboarding.domain.base.BaseTimeEntity;
import com.wanted.pre_onboarding.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "applies", uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_job_posting", columnNames = {"user_id", "job_posting_id"})
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Apply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    public Apply(User user, JobPosting jobPosting) {
        this.user = user;
        this.jobPosting = jobPosting;
        this.applicationStatus = ApplicationStatus.APPLIED;
    }

    public void updateApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
