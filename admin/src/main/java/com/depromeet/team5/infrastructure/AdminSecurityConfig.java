package com.depromeet.team5.infrastructure;

import com.depromeet.team5.domain.AdminUser;
import com.depromeet.team5.domain.AdminUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler((request, response, authentication) -> {
                    String email = ((DefaultOidcUser) authentication.getPrincipal()).getEmail();
                    Set<String> adminUserEmailSet = adminUserRepository.findAll()
                            .stream()
                            .map(AdminUser::getEmail)
                            .collect(Collectors.toSet());
                    if (!adminUserEmailSet.contains(email)) {
                        request.getSession().invalidate();
                        response.sendError(HttpStatus.FORBIDDEN.value());
                    } else {
                        response.sendRedirect("/");
                    }
                });
    }
}
