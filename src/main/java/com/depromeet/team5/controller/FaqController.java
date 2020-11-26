package com.depromeet.team5.controller;

import com.depromeet.team5.domain.faq.Faq;
import com.depromeet.team5.dto.FaqAddTagRequestDto;
import com.depromeet.team5.dto.FaqRequestDto;
import com.depromeet.team5.dto.FaqResponseDto;
import com.depromeet.team5.dto.FaqRemoveTagRequestDto;
import com.depromeet.team5.exception.FaqNotFoundException;
import com.depromeet.team5.service.FaqService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/faqs")
@RestController
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    @ApiOperation("FAQ 목록을 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping
    public ResponseEntity<List<FaqResponseDto>> getFaqList(@RequestParam(required = false) List<Long> tagIds) {
        return ResponseEntity.ok(
                faqService.getFaqList(tagIds)
                          .stream()
                          .map(FaqResponseDto::from)
                          .collect(Collectors.toList())
        );
    }

    @ApiOperation("FAQ 한 개를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponseDto> getFaq(@PathVariable Long faqId) {
        return faqService.getFaq(faqId)
                         .map(FaqResponseDto::from)
                         .map(ResponseEntity::ok)
                         .orElseThrow(() -> new FaqNotFoundException(faqId));
    }

    @ApiOperation("FAQ 를 생성합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping
    public ResponseEntity<FaqResponseDto> create(@RequestBody @Valid FaqRequestDto faqRequestDto) {
        Faq faq = faqService.createFaq(faqRequestDto.getQuestion(), faqRequestDto.getAnswer());
        return ResponseEntity.created(URI.create("/api/v1/faqs/" + faq.getFaqId()
                                                                     .toString()))
                             .body(FaqResponseDto.from(faq));
    }

    @ApiOperation("FAQ 에 태그를 추가합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/{faqId}/add-tag")
    public ResponseEntity<FaqResponseDto> addTag(
            @PathVariable Long faqId,
            @RequestBody @Valid FaqAddTagRequestDto faqAddTagRequestDto
    ) {
        return ResponseEntity.ok(
                FaqResponseDto.from(
                        faqService.addTag(faqId, faqAddTagRequestDto.getTagName())
                )
        );
    }

    @ApiOperation("FAQ 에서 태그를 삭제합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/{faqId}/remove-tag")
    public ResponseEntity<FaqResponseDto> removeTag(
            @PathVariable Long faqId,
            @RequestBody @Valid FaqRemoveTagRequestDto faqRemoveTagRequestDto
    ) {
        final Faq faq;
        if (faqRemoveTagRequestDto.getTagId() != null) {
            faq = faqService.removeTagByTagId(faqId, faqRemoveTagRequestDto.getTagId());
        } else if (faqRemoveTagRequestDto.getTagName() != null) {
            faq = faqService.removeTagByTagName(faqId, faqRemoveTagRequestDto.getTagName());
        } else {
            throw new IllegalArgumentException("tagId, tagName 중 한 가지는 꼭 입력해야합니다.");
        }
        return ResponseEntity.ok(FaqResponseDto.from(faq));
    }
}
