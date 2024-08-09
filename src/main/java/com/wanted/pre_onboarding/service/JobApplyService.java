package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Apply;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.User;
import com.wanted.pre_onboarding.dto.request.JobApply;
import com.wanted.pre_onboarding.dto.response.ApplyResponse;
import com.wanted.pre_onboarding.exception.DuplicateApplyException;
import com.wanted.pre_onboarding.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobApplyService {


    private final ApplyRepository applyRepository;
    private final UserService userService;
    private final JobPostingService jobPostingService;

    @Transactional
    public ApplyResponse apply(JobApply applyRequest) {
        User user = userService.findUser(applyRequest.getUserId());
        JobPosting jobPosting = jobPostingService.findJobPosting(applyRequest.getJobPostId());

        if (applyRepository.existsByUserAndJobPosting(user, jobPosting)) {
            throw new DuplicateApplyException("이미 해당 채용공고에 지원했습니다.");
        }
        Apply apply = new Apply(user, jobPosting);
        Apply applied = applyRepository.save(apply);

        return ApplyResponse.builder()
                .username(user.getName())
                .email(user.getEmail())
                .companyName(jobPosting.getCompany().getName())
                .position(jobPosting.getPosition())
                .applyTime(applied.getCreated())
                .build();
    }
}
