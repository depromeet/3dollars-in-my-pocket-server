package com.depromeet.team5.controller;

import com.depromeet.team5.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Profile("!prod")
@Api(value = "Token")
@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class TokenController {
    private final JwtService jwtService;

    @ApiOperation("(개발 환경 전용) User 의 token 을 조회합니다.")
    @GetMapping("/{userId}/token")
    public ResponseEntity<String> getUserToken(@PathVariable Long userId) {
        return ResponseEntity.ok(
                new JwtService.TokenRes(jwtService.create(userId)).getToken()
        );
    }
}
