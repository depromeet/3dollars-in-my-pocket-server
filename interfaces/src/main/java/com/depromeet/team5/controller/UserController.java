package com.depromeet.team5.controller;

import com.depromeet.team5.domain.user.SocialVo;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.dto.LoginDto;
import com.depromeet.team5.dto.UserDto;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.service.LoginService;
import com.depromeet.team5.service.UserService;
import com.depromeet.team5.util.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "User")
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;
    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation("로그인을 하여 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserDto userDto) {
        SocialVo socialVo = SocialVo.of(userDto.getSocialId(), userDto.getSocialType());
        User user = loginService.login(socialVo);
        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(user.getId());
        loginDto.setState(user.getState());
        loginDto.setToken(jwtService.create(user.getId()));
        return new ResponseEntity<>(loginDto, HttpStatus.OK);
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

    @ApiOperation("사용자를 탈퇴시킵니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @Auth
    @PostMapping("/signout")
    public ResponseEntity<String> signOutUser(@ModelAttribute @ApiIgnore Long userId) {
        userService.signout(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("카카오 연결 끊기 알림을 받아 탈퇴시킵니다.")
    @ApiImplicitParam(name = "Authorization", value = "KakaoAK", required = true, paramType = "header")
    @GetMapping("/deregister")
    public ResponseEntity<String> kakaoDeregister(@RequestHeader("Authorization") String header,
                                                  @RequestParam("user_id") String userId,
                                                  @RequestParam("referrer_type") String referrerType) {
        userService.kakaoDeregister(header, userId, referrerType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}