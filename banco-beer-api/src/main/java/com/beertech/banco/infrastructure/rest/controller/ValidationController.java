package com.beertech.banco.infrastructure.rest.controller;

import com.beertech.banco.infrastructure.rest.configuration.security.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.beertech.banco.infrastructure.rest.configuration.security.TokenService;
import com.beertech.banco.infrastructure.rest.controller.dto.TokenDto;
import com.beertech.banco.infrastructure.rest.controller.form.LoginForm;
import org.springframework.web.servlet.function.ServerRequest;

@RestController
@RequestMapping("/validacao")
@CrossOrigin
public class ValidationController {


    private AuthenticationTokenFilter authTokenFilter;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")
    public ResponseEntity<TokenDto> validar(@RequestHeader("Authorization") String token) {

        try {
            tokenService.isTokenValido(token);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
