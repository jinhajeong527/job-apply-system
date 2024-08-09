package com.wanted.pre_onboarding.controller;

import com.wanted.pre_onboarding.dto.request.JobApply;
import com.wanted.pre_onboarding.dto.response.ApplyResponse;
import com.wanted.pre_onboarding.dto.response.RestResponse;
import com.wanted.pre_onboarding.service.JobApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final JobApplyService jobApplyService;

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody JobApply apply) {
        ApplyResponse applied = jobApplyService.apply(apply);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RestResponse<>("성공적으로 지원하였습니다.", applied));
    }
}
