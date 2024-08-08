package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.JobPosting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    @EntityGraph(attributePaths = {"company", "usedSkills"})
    List<JobPosting> findByIsOpenTrue();
}
