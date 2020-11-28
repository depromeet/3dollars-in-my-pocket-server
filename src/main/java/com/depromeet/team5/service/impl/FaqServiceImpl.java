package com.depromeet.team5.service.impl;

import com.depromeet.team5.domain.faq.Faq;
import com.depromeet.team5.domain.faq.FaqTag;
import com.depromeet.team5.domain.faq.FaqTagMap;
import com.depromeet.team5.domain.faq.FaqTagMapId;
import com.depromeet.team5.exception.FaqNotFoundException;
import com.depromeet.team5.repository.FaqRepository;
import com.depromeet.team5.repository.FaqTagMapRepository;
import com.depromeet.team5.repository.FaqTagRepository;
import com.depromeet.team5.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
    private final FaqRepository faqRepository;
    private final FaqTagRepository faqTagRepository;
    private final FaqTagMapRepository faqTagMapRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Faq> getFaqList(Collection<Long> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return faqRepository.findAll();
        }
        return faqTagMapRepository.findByFaqTag_faqTagIdIn(tagIds)
                                  .stream()
                                  .map(FaqTagMap::getFaq)
                                  .distinct()
                                  .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Faq> getFaq(Long faqId) {
        return faqRepository.findById(faqId);
    }

    @Override
    @Transactional
    public Faq createFaq(String question, String answer) {
        return faqRepository.save(Faq.of(question, answer));
    }

    @Override
    @Transactional
    public Faq addTag(Long faqId, String tagName) {
        Faq faq = this.getFaqWithException(faqId);
        long numberOfFaqTags = faqTagRepository.count();
        FaqTag faqTag = faqTagRepository.findByName(tagName)
                                        .orElseGet(() -> faqTagRepository.save(FaqTag.from(tagName, numberOfFaqTags)));
        FaqTagMap faqTagMap = faqTagMapRepository.findById(FaqTagMapId.of(faq, faqTag))
                                                 .orElseGet(() -> faqTagMapRepository.save(FaqTagMap.of(faq, faqTag)));
        faq.addFaqTagMap(faqTagMap);
        return faq;
    }

    @Override
    @Transactional
    public Faq removeTagByTagName(Long faqId, String tagName) {
        Faq faq = this.getFaqWithException(faqId);
        Optional<FaqTag> faqTagOptional = faqTagRepository.findByName(tagName);
        if (!faqTagOptional.isPresent()) {
            return faq;
        }
        this.removeTag(faq, faqTagOptional.get());
        return faq;
    }

    @Override
    @Transactional
    public Faq removeTagByTagId(Long faqId, Long tagId) {
        Faq faq = this.getFaqWithException(faqId);
        Optional<FaqTag> faqTagOptional = faqTagRepository.findById(tagId);
        if (!faqTagOptional.isPresent()) {
            return faq;
        }
        this.removeTag(faq, faqTagOptional.get());
        return faq;
    }

    @Override
    @Transactional
    public void removeFaq(Long faqId) {
        faqRepository.findById(faqId)
                .ifPresent(faq -> {
                    List<FaqTagMap> faqTagMaps = faqTagMapRepository.findByFaq(faq);
                    faqTagMapRepository.deleteAll(faqTagMaps);
                    faqRepository.delete(faq);
                });
    }

    private Faq getFaqWithException(Long faqId) {
        return faqRepository.findById(faqId)
                            .orElseThrow(() -> new FaqNotFoundException(faqId));
    }

    private void removeTag(Faq faq, FaqTag faqTag) {
        faqTagMapRepository.findByFaqTagMapId(FaqTagMapId.of(faq, faqTag))
                           .ifPresent(it -> {
                               faq.removeFaqTagMap(it);
                               faqTagMapRepository.delete(it);
                           });
    }
}
