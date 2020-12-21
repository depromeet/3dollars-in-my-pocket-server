package com.depromeet.team5.controller;

import com.depromeet.team5.domain.ResultCode;
import com.depromeet.team5.dto.ApiResponse;
import com.depromeet.team5.dto.FailureResponse;
import com.depromeet.team5.exception.ApplicationException;
import com.depromeet.team5.exception.ForbiddenException;
import com.depromeet.team5.exception.UnauthorizedException;
import com.depromeet.team5.infrastructure.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiControllerAdvice {
    private final JwtService jwtService;

    @Value("${kakao.key.admin}")
    private String key;

    @ModelAttribute("userId")
    public Long getUserId(@RequestHeader(required = false) String authorization) {
        if (authorization == null) {
            return null;
        }
        if (authorization.equals("KakaoAK " + key)) {
            return null;
        }
        return jwtService.decode(authorization);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("Bad Request Exception", e);
        return new FailureResponse<>(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse handleUnauthorizedException(UnauthorizedException e) {
        return new FailureResponse<>(
                e.getResultCode().orElse(ResultCode.INVALID_TOKEN),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ApiResponse handleForbiddenException(ForbiddenException e) {
        return new FailureResponse<>(
                e.getResultCode().orElse(ResultCode.FORBIDDEN),
                e.getMessage()
        );
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse> handleApplicationException(ApplicationException e) {
        log.info("application Exception", e);
        ResultCode resultCode = e.getResultCode().orElse(ResultCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(resolveStatusCode(resultCode))
                .body(new FailureResponse<>(resultCode, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        log.error("Unhandled Exception", e);
        return new FailureResponse<>(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private HttpStatus resolveStatusCode(ResultCode resultCode) {
        switch (resultCode) {
            case SUCCESS:
                return HttpStatus.OK;
            case NOT_FOUND:
            case FAQ_NOT_FOUND:
            case USER_NOT_FOUND:
            case STORE_NOT_FOUND:
            case REVIEW_NOT_FOUND:
            case FAQ_TAG_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case BAD_REQUEST:
            case FAQ_TAG_NAME_DUPLICATED:
            case USER_NICKNAME_DUPLICATED:
            case STORE_DELETE_REQUEST_DUPLICATED:
                return HttpStatus.BAD_REQUEST;
            case INVALID_TOKEN:
            case UNAUTHORIZED_USER_NOT_FOUND:
            case USER_INVALID_STATUS_WITHDRAWAL:
                return HttpStatus.UNAUTHORIZED;
            case FORBIDDEN:
                return HttpStatus.FORBIDDEN;
            case INTERNAL_SERVER_ERROR:
            default:
                log.error("Failed to map result code to HttpStatus. resultCode: {}", resultCode);
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
