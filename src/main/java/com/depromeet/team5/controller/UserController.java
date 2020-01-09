package com.depromeet.team5.controller;

import com.depromeet.team5.domain.User;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.service.LoginService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "User")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;

    @ApiOperation("로그인을 하여 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(loginService.login(userDto), HttpStatus.OK);
    }

    @ApiOperation("사용자의 정보를 조회합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @GetMapping("/info")
    public ResponseEntity<User> userInfo(@RequestParam Long userId) {
        return new ResponseEntity<>(loginService.userInfo(userId), HttpStatus.OK);
    }

    @ApiOperation("사용자의 닉네임을 설정합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PutMapping("/nickname")
    public ResponseEntity<String> setNickname(@RequestParam Long userId, @RequestParam String nickName) {
        loginService.setNickname(userId, nickName);
        return new ResponseEntity<>("nickname update success", HttpStatus.OK);
    }
}