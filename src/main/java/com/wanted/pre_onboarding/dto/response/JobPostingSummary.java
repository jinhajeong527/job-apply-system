package com.wanted.pre_onboarding.dto.response;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.vo.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JobPostingSummary {

        private Long postId;
        private String companyName;
        private String country;
        private String region;
        private String position;
        private Integer reward;
        private Set<String> usedSkills;

        @Builder
        public JobPostingSummary(Long postId, String companyName, String country, String region, String position, Integer reward, Set<String> usedSkills) {
                this.postId = postId;
                this.companyName = companyName;
                this.country = country;
                this.region = region;
                this.position = position;
                this.reward = reward;
                this.usedSkills = usedSkills;
        }

        public static JobPostingSummary fromEntity(JobPosting jp) {
                Company company = jp.getCompany();
                Address address = company.getAddress();

                return JobPostingSummary.builder()
                        .postId(jp.getId())
                        .companyName(company.getName())
                        .country(address.getCountry())
                        .region(address.getDistrict())
                        .position(jp.getPosition())
                        .reward(jp.getReward())
                        .usedSkills(jp.getUsedSkills())
                        .build();
        }

}
