package com.wanted.pre_onboarding.controller;

import com.wanted.pre_onboarding.dto.request.JobPostingRequest;
import com.wanted.pre_onboarding.dto.response.JobPostingResponse;
import com.wanted.pre_onboarding.dto.response.RestResponse;
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

    @PutMapping("/{postId}")
    public ResponseEntity<JobPostingResponse> edit(@PathVariable("postId") Long postId,
                                                   @Valid @RequestBody JobPostingRequest request) {
        JobPostingResponse response = postingService.edit(postId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId) {
        RestResponse<JobPostingResponse> response = postingService.delete(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
