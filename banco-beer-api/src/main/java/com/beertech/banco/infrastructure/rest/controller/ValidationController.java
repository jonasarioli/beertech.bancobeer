package com.beertech.banco.infrastructure.rest.controller;

import antlr.TokenStreamException;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.infrastructure.rest.configuration.security.AuthenticationTokenFilter;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
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

    @Autowired
    private ContaService contaService;

    @PostMapping("/token")
    public ResponseEntity<ContaDto> validar(@RequestHeader("Authorization") String token) {

        try {
            tokenService.isTokenValido(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
        try {
            token = token.substring(7);
            Long contaId = tokenService.getIdConta(token);
            ContaDto contaResponse = new ContaDto(contaService.contaPeloId(contaId));
            return ResponseEntity.ok(contaResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
