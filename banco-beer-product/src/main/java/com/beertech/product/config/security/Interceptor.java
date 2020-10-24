package com.beertech.product.config.security;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.beertech.banco.infrastructure.rest.controller.ValidationController;
//import com.beertech.banco.infrastructure.rest.controller.dto.TokenDto;
import com.beertech.product.controller.dto.TokenDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class Interceptor implements HandlerInterceptor {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

       if (true){
           RestTemplate restTemplate = new RestTemplate();
           TokenDto token = restTemplate.getForObject(
                   "http://localhost:8080/beercoins/validacao/token", TokenDto.class);
        } else {

            return false;
        }
        return false;
    }

}
