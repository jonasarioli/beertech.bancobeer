package com.beertech.product.controller.dto;


import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public class ContaDto {

    private String hash;
    private BigDecimal saldo;
    private String nome;
    private String email;
    private String cnpj;

    public ContaDto(){

    };

    public ContaDto(String hash, BigDecimal saldo, String nome, String email, String cnpj) {
        this.hash = hash;
        this.saldo = saldo;
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
    }



    public String getHash() {
        return hash;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}

