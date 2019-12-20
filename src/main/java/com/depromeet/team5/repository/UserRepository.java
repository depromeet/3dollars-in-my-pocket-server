package com.depromeet.team5.repository;

import com.depromeet.team5.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findBySocialId(Long socialId);
}
