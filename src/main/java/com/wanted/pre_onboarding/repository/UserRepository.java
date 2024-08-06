package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
