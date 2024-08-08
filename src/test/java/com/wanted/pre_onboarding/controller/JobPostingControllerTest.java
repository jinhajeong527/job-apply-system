package com.wanted.pre_onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.service.JobPostingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsInAnyOrder;

@WebMvcTest
class JobPostingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JobPostingService postingService;

    @Test
    @DisplayName("채용공고 등록 성공 테스트")
    void should_ReturnCreatedResponse_When_ValidRequestProvided() throws Exception {
        Set<String> skillSet = Set.of("Python", "Django");
        JobPostingResponse jpResponse = new JobPostingResponse(1L, "Python 개발자",
                "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
                1000000, LocalDateTime.now(), skillSet);
        when(postingService.register(any(JobPostingRequest.class))).thenReturn(jpResponse);

        JobPostingRequest jpRequest = new JobPostingRequest(1L, "Python 개발자", 1000000,
                "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", skillSet);

        mockMvc.perform(post("/job-posting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registeredId").value(jpResponse.getRegisteredId()))
                .andExpect(jsonPath("$.position").value(jpResponse.getPosition()))
                .andExpect(jsonPath("$.reward").value(jpResponse.getReward()))
                .andExpect(jsonPath("$.usedSkills").isArray())
                .andExpect(jsonPath("$.usedSkills", containsInAnyOrder("Python", "Django")));
    }

    @Test
    @DisplayName("잘못된 요청 데이터로 인한 400 Bad Request 응답 테스트")
    void shouldReturnBadRequest_WhenJsonIsMalformed() throws Exception {
        // 잘못된 JSON 데이터 정의
        String malformedJson = "{\"companyId\": \"notLongValue\", " +
                "\"position\": \"Python 개발자\", " +
                "\"reward\": 1000000, " +
                "\"description\": \"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..\", " +
                "\"usedSkills\": [\"Python\", \"Django\"]}";
        mockMvc.perform(post("/job-posting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.results[0].field").value("companyId"))
                .andExpect(jsonPath("$.results[0].rejectedValue").value("notLongValue"))
                .andExpect(jsonPath("$.results[0].message").value("Invalid Format"));
    }

    @Test
    @DisplayName("유효성 검증 실패 400 Bad Request 응답 테스트")
    void shouldReturnBadRequest_WhenValidationFails() throws Exception {
        // 유효성 검증 실패 데이터
        JobPostingRequest invalidRequest =
                JobPostingRequest.builder()
                        .companyId(null)
                        .position("")
                        .reward(0)
                        .description("")
                        .usedSkills(Set.of())
                        .build();

        mockMvc.perform(post("/job-posting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.results[*].field",
                        hasItems("companyId", "position", "reward", "description", "usedSkills")))
                .andExpect(jsonPath("$.results[*].message",
                        hasItems("Company ID is required.", "Position is required.",
                        "Reward must be greater than zero.", "Description is required.",
                        "At least one skill must be specified."))
                );
    }
}