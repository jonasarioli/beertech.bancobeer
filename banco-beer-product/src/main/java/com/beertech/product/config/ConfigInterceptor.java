package com.beertech.product.config;

import com.beertech.product.config.security.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class ConfigInterceptor extends WebMvcConfigurerAdapter {

    @Bean
    Interceptor interceptorTest() {
        return new Interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interceptorTest());
    }
}