package com.depromeet.team5.controller;

import com.depromeet.team5.dto.FaqTagResponseDto;
import com.depromeet.team5.service.FaqTagService;
import com.depromeet.team5.application.security.Auth;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/faq-tags")
@RestController
@RequiredArgsConstructor
public class FaqTagController {
    private final FaqTagService faqTagService;

    @ApiOperation("FAQ 태그 목록을 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping
    public ResponseEntity<List<FaqTagResponseDto>> getFaqTags() {
        return ResponseEntity.ok(
                faqTagService.getFaqTags()
                             .stream()
                             .map(FaqTagResponseDto::from)
                             .collect(Collectors.toList())
        );
    }

    @ApiOperation("FAQ 태그 이름을 수정합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/{faqTagId}/rename")
    public ResponseEntity<FaqTagResponseDto> rename(@PathVariable Long faqTagId, @RequestParam String name) {
        return ResponseEntity.ok(
                FaqTagResponseDto.from(
                        faqTagService.rename(faqTagId, name)
                )
        );
    }
}
