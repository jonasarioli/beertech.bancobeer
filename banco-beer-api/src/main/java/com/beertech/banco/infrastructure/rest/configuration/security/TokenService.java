package com.beertech.banco.infrastructure.rest.configuration.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;


    public String createTokan(Authentication authentication) {
        MySqlConta logado = (MySqlConta) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        Map<String, Object> claims = new HashMap<>();
        claims.put("Nome", logado.getNome());
        claims.put("Perfil", logado.getProfiles().iterator().next().getName());
        claims.put("Saldo", logado.getSaldo());
        claims.put("Hash", logado.getHash());

        return Jwts.builder()
                .setIssuer("BeerCoins API ")
                .setClaims(claims)
                .setSubject(logado.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Long getIdConta(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}
