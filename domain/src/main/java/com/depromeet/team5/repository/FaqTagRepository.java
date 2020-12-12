package com.depromeet.team5.repository;

import com.depromeet.team5.domain.faq.FaqTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqTagRepository extends JpaRepository<FaqTag, Long> {
    Optional<FaqTag> findByName(String name);

    boolean existsByName(String name);
}
