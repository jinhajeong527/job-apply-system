package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.config.DataInitializer;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@Transactional
@Import(DataInitializer.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
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
    @DisplayName("존재하는 채용 공고 수정 시 성공")
    void shouldEditJobPosting_WithValidJobPostRequest() {
        JobPostingRequest editRequest = JobPostingRequest.builder()
                .companyId(1L)
                .position("Java 개발자")
                .reward(1500000)
                .description("원티드랩에서 주니어 Java 개발자를 채용합니다.")
                .usedSkills(Set.of("Java", "Spring"))
                .build();
        // when
        JobPostingResponse response = jobPostingService.edit(1L, editRequest);
        assertThat(response).isNotNull();
        assertThat(response.getPosition()).isEqualTo("Java 개발자");
        assertThat(response.getReward()).isEqualTo(1500000);
        assertThat(response.getDescription()).isEqualTo("원티드랩에서 주니어 Java 개발자를 채용합니다.");
        assertThat(response.getUsedSkills()).containsAll(Set.of("Java", "Spring"));
    }

    @Test
    @DisplayName("존재하지 않는 채용 공고 수정 시 EntityNotFoundException 발생")
    void shouldThrowException_WhenEditingNonExistentJobPosting() {
        JobPostingRequest editRequest = JobPostingRequest.builder()
                .companyId(1L)
                .position("Java 개발자")
                .reward(1500000)
                .description("원티드랩에서 Java 개발자를 채용합니다.")
                .usedSkills(Set.of("Java", "Spring"))
                .build();
        // when & then
        assertThatThrownBy(() -> jobPostingService.edit(999L, editRequest)) // 존재하지 않는 JobPosting Id 사용
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("요청한 채용공고를 찾지 못했습니다.");
    }

    @Test
    @DisplayName("유효하지 않은 회사 ID로 채용 공고 수정 시 EntityNotFoundException 발생")
    void shouldThrowException_WhenEditingWithInvalidCompanyId() {
        JobPostingRequest editRequest = JobPostingRequest.builder()
                .companyId(999L)
                .position("Java 개발자")
                .reward(1500000)
                .description("원티드랩에서 Java 개발자를 채용합니다.")
                .usedSkills(Set.of("Java", "Spring"))
                .build();
        // when & then
        assertThatThrownBy(() -> jobPostingService.edit(1L, editRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("요청한 회사 정보를 찾지 못했습니다");
    }

    @Test
    @DisplayName("존재하는 채용공고 삭제 요청 시 성공적으로 삭제 완료")
    void shouldDeleteJobPosting_WhenDeleteRequestWithExistingId() {
        assertThat(jobPostingRepository.findById(1L)).isPresent();
        jobPostingService.delete(1L);
        assertThat(jobPostingRepository.findById(1L)).isEmpty();
    }

    @Test
    @DisplayName("미존재하는 채용공고 삭제 요청 시 EntityNotFound 예외 발생")
    void shouldThrowEntityNotFoundException_WhenDeleteRequestWithNonExistingId() {
        assertThat(jobPostingRepository.findById(999L)).isEmpty();
        assertThatThrownBy(() -> jobPostingService.delete(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("요청한 채용공고를 찾지 못했습니다.");
    }

    @Test
    @DisplayName("이미 삭제된 채용공고 삭제 요청 시 EntityNotFound 예외 발생")
    void shouldThrowEntityNotFoundException_WhenDeleteAlreadyDeletedPost() {
        assertThat(jobPostingRepository.findById(1L)).isPresent();
        jobPostingService.delete(1L);
        assertThatThrownBy(() -> jobPostingService.delete(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("요청한 채용공고를 찾지 못했습니다.");
    }
}