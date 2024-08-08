package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    @EntityGraph(attributePaths = {"company", "usedSkills"})
    List<JobPosting> findByIsOpenTrue();

    @Override
    @EntityGraph(attributePaths = {"company", "usedSkills"})
    Optional<JobPosting> findById(@Param("id") Long postId);

    @Query("SELECT jp FROM JobPosting jp WHERE jp.company = :company AND jp.id <> :excludedId")
    List<JobPosting> findByCompanyAndExcludeId(@Param("company") Company company, @Param("excludedId") Long excludedId);
}
