package com.beertech.banco.infrastructure.rest.configuration.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.beertech.banco.infrastructure.repository.mysql.ContaRepository;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;


public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private ContaRepository repository;

    public AuthenticationTokenFilter(TokenService tokenService, ContaRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        boolean valido = tokenService.isTokenValido(token);

        if (valido) {
            contaAuthenticate(token);
        }

        filterChain.doFilter(request, response);

    }

    private void contaAuthenticate(String token) {
        Long contaId = tokenService.getIdConta(token);
        Optional<MySqlConta> conta = repository.findById(contaId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(conta.get(), null, conta.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }

}
