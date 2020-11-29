package com.depromeet.team5.repository;

import com.depromeet.team5.domain.faq.Faq;
import com.depromeet.team5.domain.faq.FaqTagMap;
import com.depromeet.team5.domain.faq.FaqTagMapId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FaqTagMapRepository extends JpaRepository<FaqTagMap, FaqTagMapId> {
    List<FaqTagMap> findByFaqTag_faqTagIdIn(Collection<Long> tagNames);

    Optional<FaqTagMap> findByFaqTagMapId(FaqTagMapId faqTagMapId);

    List<FaqTagMap> findByFaq(Faq faq);
}
