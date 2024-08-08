package com.wanted.pre_onboarding.controller;

import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.service.JobPostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-posting")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobPostingService postingService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobPostingResponse> register(@Valid @RequestBody JobPostingRequest request) {
        JobPostingResponse response = postingService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<JobPostingResponse> edit(@PathVariable("id") Long postId,
                                                   @Valid @RequestBody JobPostingRequest request) {
        JobPostingResponse response = postingService.edit(postId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
