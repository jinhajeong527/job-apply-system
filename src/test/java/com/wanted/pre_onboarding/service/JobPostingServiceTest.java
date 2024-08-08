package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.exception.EntityNotFoundException;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JobPostingServiceTest {

    private final JobPostingService jobPostingService;
    private final CompanyRepository companyRepository;
    private final JobPostingRepository jobPostingRepository;
    @Autowired
    JobPostingServiceTest(JobPostingService jobPostingService, CompanyRepository companyRepository, JobPostingRepository jobPostingRepository) {
        this.jobPostingService = jobPostingService;
        this.companyRepository = companyRepository;
        this.jobPostingRepository = jobPostingRepository;
    }
    Company company;
    JobPosting savedJobPosting;
    JobPostingRequest editRequest;

    @BeforeEach
    void setUp() {
        company = companyRepository.findById(1L)
                .orElseThrow(EntityNotFoundException::new);
        JobPosting jobPosting = JobPosting.builder()
                .position("Python 개발자")
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
                .reward(1000000)
                .usedSkills(Set.of("Python", "Django"))
                .company(company)
                .build();
        savedJobPosting = jobPostingRepository.save(jobPosting);

        editRequest = JobPostingRequest.builder()
                .companyId(company.getId())
                .position("Java 개발자")
                .reward(1500000)
                .description("원티드랩에서 Java 개발자를 채용합니다.")
                .usedSkills(Set.of("Java", "Spring"))
                .build();
    }

    @Test
    void shouldEditJobPosting_WithValidJobPostRequest() {
        // when
        JobPostingResponse response = jobPostingService.edit(savedJobPosting.getId(), editRequest);
        assertThat(response).isNotNull();
        assertThat(response.getPosition()).isEqualTo("Java 개발자");
        assertThat(response.getReward()).isEqualTo(1500000);
        assertThat(response.getDescription()).isEqualTo("원티드랩에서 Java 개발자를 채용합니다.");
        assertThat(response.getUsedSkills()).containsAll(Set.of("Java", "Spring"));
    }

    @Test
    @DisplayName("존재하지 않는 채용 공고 수정 시 EntityNotFoundException 발생")
    void shouldThrowException_WhenEditingNonExistentJobPosting() {
        // when & then
        assertThatThrownBy(() -> jobPostingService.edit(999L, editRequest)) // 존재하지 않는 ID 사용
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("수정을 요청한 채용공고를 찾지 못했습니다.");
    }

    @Test
    @DisplayName("유효하지 않은 회사 ID로 채용 공고 수정 시 EntityNotFoundException 발생")
    void shouldThrowException_WhenEditingWithInvalidCompanyId() {
        editRequest.setCompanyId(999L); // 존재하지 않는 회사 ID로 요청
        // when & then
        assertThatThrownBy(() -> jobPostingService.edit(savedJobPosting.getId(), editRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("요청한 회사 정보를 찾지 못했습니다");
    }
}