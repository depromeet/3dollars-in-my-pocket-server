package com.depromeet.team5.repository;

import com.depromeet.team5.domain.store.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
