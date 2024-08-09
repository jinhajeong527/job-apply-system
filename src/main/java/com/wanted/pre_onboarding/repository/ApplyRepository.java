package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.Apply;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByUserAndJobPosting(User user, JobPosting jobPosting);
}
