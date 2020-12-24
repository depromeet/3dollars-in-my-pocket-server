package com.depromeet.team5.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SleepFilter extends OncePerRequestFilter {
    private static final String SLEEP_HEADER_NAME = "X-3DOLLAR-SLEEP-MILLISECONDS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        sleepIfNeed(request);
        filterChain.doFilter(request, response);
    }

    private void sleepIfNeed(HttpServletRequest request) {
        Long sleepMillis = this.resolveSleepHeader(request);
        if (sleepMillis == null) {
            return;
        }
        try {
            log.debug("Sleep {}ms", sleepMillis);
            Thread.sleep(sleepMillis);
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }

    private Long resolveSleepHeader(HttpServletRequest request) {
        String header = request.getHeader(SLEEP_HEADER_NAME);
        try {
            return Long.parseLong(header);
        } catch (NumberFormatException ignore) {
            return null;
        }
    }
}
