package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.config.DataInitializer;
import com.wanted.pre_onboarding.domain.Apply;
import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.User;
import com.wanted.pre_onboarding.dto.request.JobApply;
import com.wanted.pre_onboarding.dto.response.ApplyResponse;
import com.wanted.pre_onboarding.exception.DuplicateApplyException;
import com.wanted.pre_onboarding.exception.EntityNotFoundException;
import com.wanted.pre_onboarding.repository.ApplyRepository;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@Transactional
@Import(DataInitializer.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class JobApplyServiceTest {

    private final JobApplyService jobApplyService;
    private final UserService userService;
    private final JobPostingService jobPostingService;

    private final JobPostingRepository jobPostingRepository;
    private final ApplyRepository applyRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public JobApplyServiceTest(JobApplyService jobApplyService, UserService userService,
                               JobPostingService jobPostingService, JobPostingRepository jobPostingRepository,
                               ApplyRepository applyRepository, CompanyRepository companyRepository) {
        this.jobApplyService = jobApplyService;
        this.userService = userService;
        this.jobPostingService = jobPostingService;
        this.jobPostingRepository = jobPostingRepository;
        this.applyRepository = applyRepository;
        this.companyRepository = companyRepository;
    }

    @BeforeEach
    void setUp() {

        Company wantedLab = companyRepository.findById(1L)
                .orElseThrow(EntityNotFoundException::new);

        JobPosting jobPosting1 = JobPosting.builder()
                .position("Python 개발자")
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
                .reward(1000000)
                .usedSkills(Set.of("Python", "Django"))
                .company(wantedLab)
                .build();
        jobPostingRepository.save(jobPosting1);
    }

    @Test
    @DisplayName("성공적으로 지원하기")
    void shouldApplySuccessfully_WhenValidRequest() {

        User user = userService.findUser(1L);
        JobPosting jobPosting = jobPostingService.findJobPosting(1L);

        // given
        JobApply applyRequest = JobApply
                .builder()
                .userId(1L)
                .jobPostId(1L)
                .build();

        // when
        ApplyResponse response = jobApplyService.apply(applyRequest);

        // then
        assertThat(response.getUsername()).isEqualTo(user.getName());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getPosition()).isEqualTo(jobPosting.getPosition());
        assertThat(response.getCompanyName()).isEqualTo(jobPosting.getCompany().getName());
        assertThat(applyRepository.existsByUserAndJobPosting(user, jobPosting)).isTrue();
    }

    @Test
    @DisplayName("중복 지원 시 DuplicateApplyException 발생")
    void shouldThrowException_WhenDuplicateApply() {
        User user = userService.findUser(1L);
        JobPosting jobPosting = jobPostingService.findJobPosting(1L);
        // given
        Apply apply = new Apply(user, jobPosting);
        applyRepository.save(apply);

        JobApply applyRequest = JobApply.builder()
                .userId(user.getId())
                .jobPostId(jobPosting.getId())
                .build();

        // when & then
        assertThatThrownBy(() -> jobApplyService.apply(applyRequest))
                .isInstanceOf(DuplicateApplyException.class)
                .hasMessageContaining("이미 해당 채용공고에 지원했습니다.");
    }
}