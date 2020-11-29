package com.depromeet.team5.controller;

import com.depromeet.team5.domain.ResultCode;
import com.depromeet.team5.dto.ApiResponse;
import com.depromeet.team5.dto.FailureResponse;
import com.depromeet.team5.exception.ApplicationException;
import com.depromeet.team5.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
@PropertySource("classpath:key.properties")
public class ApiControllerAdvice {
    private final JwtService jwtService;

    @Value("${key.admin}")
    private String key;

    @ModelAttribute
    public Long getUserId(@RequestHeader(required = false) String authorization) {
        if (authorization == null) {
            return null;
        }
        if (authorization.equals("KakaoAK " + key)) {
            return null;
        }
        return jwtService.decode(authorization).getUserId();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("Invalid request", e);
        return new FailureResponse<>(
                ResultCode.BAD_REQUEST,
                e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", "))
        );
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse> handleApplicationException(ApplicationException e) {
        log.info("application Exception", e);
        return ResponseEntity.status(e.getHttpStatus())
                .body(new FailureResponse<>(
                        e.getResultCode().orElse(ResultCode.INTERNAL_SERVER_ERROR),
                        e.getMessage()
                ));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        log.error("Unhandled Exception", e);
        return new FailureResponse<>(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
