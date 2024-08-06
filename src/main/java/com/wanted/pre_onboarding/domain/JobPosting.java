package com.wanted.pre_onboarding.domain;

import com.wanted.pre_onboarding.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "job_postings")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class JobPosting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_posting_id")
    private Long id;

    private String position;

    @Lob
    private String description;

    private int reward;

    @ElementCollection
    @CollectionTable(name = "used_skills",
            joinColumns = @JoinColumn(name = "job_posting_id"),
            foreignKey = @ForeignKey(name = "FK_job_postings_used_skills")
    )
    @Column(name = "skill_name")
    private Set<String> usedSkills;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public JobPosting(String position, String description, int reward, Set<String> usedSkills, Company company) {
        this.position = position;
        this.description = description;
        this.reward = reward;
        this.usedSkills = usedSkills;
        this.company = company;
    }
}
