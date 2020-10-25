package com.beertech.product.config.security;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.beertech.banco.infrastructure.rest.controller.ValidationController;
//import com.beertech.banco.infrastructure.rest.controller.dto.TokenDto;
import com.beertech.product.controller.dto.TokenDto;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;


@Component
public class Interceptor implements HandlerInterceptor {

    @Value("${server.validation}")
    private String url ;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        Object result = null;
        if (accessToken != null){
           if (accessToken.contains("Basic")){return true;}
           HttpHeaders headers = new HttpHeaders();
           RestTemplate restTemplate = new RestTemplate();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.set("Authorization", accessToken);
           HttpEntity<String> entity = new HttpEntity<String>(null,headers);
           try {
               result = restTemplate.postForObject("http://localhost:8080/validacao/token", entity, Object.class);
           } catch (RestClientException e) {
               e.printStackTrace();
               return false;
           }
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

           return true;

        } else {
           if (request.getRequestURI().contains("swagger") || request.getRequestURI().contains("webjars")){return true;}
           if (request.getRequestURI() == "/swagger-ui.html"){return true;}
           return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
