package com.depromeet.team5.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public class RequestUtils {
    private RequestUtils() {
    }

    public static Optional<HttpServletRequest> getHttpServletRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(it -> it instanceof ServletRequestAttributes)
                .map(it -> (ServletRequestAttributes) it)
                .map(ServletRequestAttributes::getRequest);
    }
}
