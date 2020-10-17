package com.beertech.banco.infrastructure.rest.controller.dto;

import java.math.BigDecimal;

import com.beertech.banco.domain.Conta;

public class ContaDto {

	private String hash;
	private BigDecimal saldo;
	private String nome;
	private String email;
	private String cnpj;
	
	
	
	public ContaDto(String hash, BigDecimal saldo, String nome, String email, String cnpj) {
		this.hash = hash;
		this.saldo = saldo;
		this.nome = nome;
		this.email = email;
		this.cnpj = cnpj;
	}
	
	public ContaDto(Conta conta) {
		this.hash = conta.getHash();
		this.saldo = conta.getSaldo();
		this.nome = conta.getNome();
		this.email = conta.getEmail();
		this.cnpj = conta.getCnpj();
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


}
