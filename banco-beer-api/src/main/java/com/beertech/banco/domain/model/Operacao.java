package com.beertech.banco.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public class Operacao {	

	private Long id;
	private LocalDateTime dataHora;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoOperacao tipo;
	private  String nomeContaDestino;
	private Conta conta;

	public Operacao() {
	}
	
	public Operacao(BigDecimal valor, TipoOperacao tipo, String hashContaDestino) {
		this.dataHora = LocalDateTime.now();
		this.valor = valor;
		this.tipo = tipo;
		this.nomeContaDestino = hashContaDestino;
	}

	public Operacao(LocalDateTime dataHora, BigDecimal valor, TipoOperacao tipo, String hashContaDestino) {
		this.dataHora = dataHora;
		this.valor = valor;
		this.tipo = tipo;
		this.nomeContaDestino = hashContaDestino;		
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setTipo(TipoOperacao tipo) {
		this.tipo = tipo;
	}

	public void setNomeContaDestino(String hashContaDestino) { this.nomeContaDestino = hashContaDestino; }

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public TipoOperacao getTipo() {
		return tipo;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public String getNomeContaDestino() { return nomeContaDestino; }
}
