package com.depromeet.team5.service;

import com.depromeet.team5.domain.faq.FaqTag;

import java.util.List;

public interface FaqTagService {
    List<FaqTag> getFaqTags();

    FaqTag rename(Long faqTagId, String name);
}
