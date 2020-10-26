package com.beertech.banco.infrastructure.rest.controller;

import antlr.TokenStreamException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.EPerfil;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.infrastructure.rest.configuration.security.AuthenticationTokenFilter;
import com.beertech.banco.infrastructure.rest.controller.dto.ContaDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import springfox.documentation.annotations.ApiIgnore;

import static io.jsonwebtoken.Jwts.parser;

@RestController
@RequestMapping("/beercoins/validacao")
@CrossOrigin
public class ValidationController {


    private AuthenticationTokenFilter authTokenFilter;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ContaService contaService;

    @ApiIgnore
    @PostMapping("/token")
    public ResponseEntity<ContaDto> validar(@RequestHeader("Authorization") String token) {

        try {
            if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            token = token.substring(7, token.length());
            boolean tokenValido = tokenService.isTokenValido(token);
            if(!tokenValido) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Long contaId = tokenService.getIdConta(token);
            Conta conta = contaService.contaPeloId(contaId);
            if(conta.getProfiles().iterator().next().getName().equals(EPerfil.ADMIN)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(new ContaDto(conta));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
