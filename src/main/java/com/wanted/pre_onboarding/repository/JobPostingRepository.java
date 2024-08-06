package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}
