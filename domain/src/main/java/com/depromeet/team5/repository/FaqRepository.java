package com.depromeet.team5.repository;

import com.depromeet.team5.domain.faq.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
