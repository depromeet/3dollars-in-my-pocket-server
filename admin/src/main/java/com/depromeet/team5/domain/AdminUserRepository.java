package com.depromeet.team5.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
