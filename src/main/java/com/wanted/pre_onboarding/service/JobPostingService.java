package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyService companyService;
    @Transactional
    public JobPostingResponse registerJobPosting(JobPostingRequest request) {

        Company foundCompany = companyService.findCompanyById(request.getCompanyId());

        JobPosting jobPosting = JobPosting.builder()
                .position(request.getPosition())
                .description(request.getDescription())
                .reward(request.getReward())
                .usedSkills(request.getUsedSkills())
                .company(foundCompany)
                .build();

        JobPosting saved = jobPostingRepository.save(jobPosting);
        return JobPostingResponse.fromEntity(saved);
    }
}
