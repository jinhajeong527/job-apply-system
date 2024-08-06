package com.wanted.pre_onboarding.config;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.User;
import com.wanted.pre_onboarding.domain.vo.Address;
import com.wanted.pre_onboarding.enums.Role;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        // Company 테스트 데이터 삽입
        Company wantedLab = Company.builder()
                .name("원티드랩")
                .industryName("IT, 컨텐츠")
                .address(Address.builder()
                        .country("한국")
                        .city("서울")
                        .district("송파구")
                        .street("올림픽로 300")
                        .building("롯데월드타워 35층")
                        .zipcode("05551")
                        .build())
                .build();
        companyRepository.save(wantedLab);

        Company naver = Company.builder()
                .name("네이버")
                .industryName("IT, 컨텐츠")
                .address(Address.builder()
                        .country("한국")
                        .city("경기")
                        .district("성남시")
                        .street("정자일로 95")
                        .zipcode("13561")
                        .build())
                .build();
        companyRepository.save(naver);

        // User 테스트 데이터 삽입
        User user1 = User.builder()
                .name("user1")
                .password("password1")
                .email("user1@gmail.com")
                .role(Role.USER)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .name("user2")
                .password("password2")
                .email("user2@gmail.com")
                .role(Role.USER)
                .build();
        userRepository.save(user2);
    }
}
