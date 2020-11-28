package com.depromeet.team5.service;

import com.depromeet.team5.domain.faq.Faq;
import com.depromeet.team5.domain.faq.FaqContentVo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FaqService {
    List<Faq> getFaqList(Collection<Long> tagIds);

    Optional<Faq> getFaq(Long faqId);

    Faq createFaq(String question, String answer);

    Faq addTag(Long faqId, String tagName);

    Faq removeTagByTagName(Long faqId, String tagName);

    Faq removeTagByTagId(Long faqId, Long faqTagId);

    Faq update(Long faqId, FaqContentVo faqContentVo);

    void removeFaq(Long faqId);

}
