package com.depromeet.team5.controller;

import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.service.LoginService;
import com.depromeet.team5.util.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(loginService.login(userDto), HttpStatus.OK);
    }

    @Auth
    @GetMapping("/info")
    public ResponseEntity<User> userInfo(@RequestParam Long userId) {
        return new ResponseEntity<>(loginService.userInfo(userId), HttpStatus.OK);
    }

    @Auth
    @PutMapping("/nickname")
    public ResponseEntity<String> setNickname(@RequestParam Long userId, @RequestParam String nickName) {
        loginService.setNickname(userId, nickName);
        return new ResponseEntity<>("nickname update success", HttpStatus.OK);
    }
}