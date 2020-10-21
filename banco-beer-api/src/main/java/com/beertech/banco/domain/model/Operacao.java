package com.beertech.banco.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public class Operacao {	

	private LocalDateTime dataHora;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoOperacao tipo;
	private  String hashContaDestino;

	public Operacao() {
	}
	
	public Operacao(BigDecimal valor, TipoOperacao tipo, String hashContaDestino) {
		this.dataHora = LocalDateTime.now();
		this.valor = valor;
		this.tipo = tipo;
		this.hashContaDestino = hashContaDestino;
	}

	public Operacao(LocalDateTime dataHora, BigDecimal valor, TipoOperacao tipo, String hashContaDestino) {
		this.dataHora = dataHora;
		this.valor = valor;
		this.tipo = tipo;
		this.hashContaDestino = hashContaDestino;
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

	public void setHashContaDestino(String hashContaDestino) { this.hashContaDestino = hashContaDestino; }

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public TipoOperacao getTipo() {
		return tipo;
	}

	public String getHashContaDestino() { return hashContaDestino; }
}
