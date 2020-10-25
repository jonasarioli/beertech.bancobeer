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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

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
       if (accessToken != null){
           HttpHeaders headers = new HttpHeaders();
           RestTemplate restTemplate = new RestTemplate();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.set("Authorization", accessToken);
           HttpEntity<String> entity = new HttpEntity<String>(null,headers);
           try {
               Object result = restTemplate.postForObject("http://localhost:8080/validacao/token", entity, Object.class);
           } catch (RestClientException e) {
               e.printStackTrace();
               return false;
           }
           return true;

        } else {

           return false;
        }

    }

}
