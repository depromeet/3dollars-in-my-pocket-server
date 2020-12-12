package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.faq.FaqTag;
import com.depromeet.team5.exception.FaqTagNotFoundException;
import com.depromeet.team5.exception.FaqTagNameDuplicatedException;
import com.depromeet.team5.repository.FaqTagRepository;
import com.depromeet.team5.service.FaqTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqTagServiceImpl implements FaqTagService {

    private final FaqTagRepository faqTagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<FaqTag> getFaqTags() {
        return faqTagRepository.findAll();
    }

    @Override
    @Transactional
    public FaqTag rename(Long faqTagId, String name) {
        if (faqTagRepository.existsByName(name)) {
            throw new FaqTagNameDuplicatedException(faqTagId, name);
        }
        return faqTagRepository.findById(faqTagId)
                .map(it -> it.rename(name))
                .orElseThrow(() -> new FaqTagNotFoundException(faqTagId));

    }
}
