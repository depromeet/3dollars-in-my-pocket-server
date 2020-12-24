package com.depromeet.team5.infrastructure.spring;

import com.depromeet.team5.filter.SleepFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Profile("!prod")
    @Bean
    public FilterRegistrationBean<SleepFilter> sleepFilterFilterRegistrationBean() {
        FilterRegistrationBean<SleepFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SleepFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
