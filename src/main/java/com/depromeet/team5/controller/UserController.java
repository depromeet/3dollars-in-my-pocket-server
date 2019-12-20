package com.depromeet.team5.controller;

import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.TokenDto;
import com.depromeet.team5.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody TokenDto tokenDto) {
        return new ResponseEntity<>(loginService.login(tokenDto), HttpStatus.OK);
    }
}
