package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.exception.EntityNotFoundException;
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
    public JobPostingResponse register(JobPostingRequest request) {

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
    @Transactional
    public JobPostingResponse edit(Long postId, JobPostingRequest request) {
        companyService.findCompanyById(request.getCompanyId());

        JobPosting foundJp = jobPostingRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("수정을 요청한 채용공고를 찾지 못했습니다."));

        foundJp.updatePosition(request.getPosition());
        foundJp.updateReward(request.getReward());
        foundJp.updateUsedSkills(request.getUsedSkills());
        foundJp.updateDescription(request.getDescription());

        return JobPostingResponse.fromEntity(foundJp);
    }
}
