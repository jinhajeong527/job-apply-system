package com.wanted.pre_onboarding.controller;

import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-posting")
@RequiredArgsConstructor
public class JobPostingController {
    private final JobPostingService postingService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobPostingResponse> registerJobPosting(@RequestBody JobPostingRequest request) {
        JobPostingResponse response = postingService.registerJobPosting(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
