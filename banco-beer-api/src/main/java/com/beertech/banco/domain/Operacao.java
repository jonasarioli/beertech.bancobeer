package com.beertech.banco.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class Operacao {	

	private LocalDateTime dataHora;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoOperacao tipo;

	public Operacao() {
	}
	
	public Operacao(BigDecimal valor, TipoOperacao tipo) {
		this.dataHora = LocalDateTime.now();
		this.valor = valor;
		this.tipo = tipo;
	}

	public Operacao(LocalDateTime dataHora, BigDecimal valor, TipoOperacao tipo) {
		this.dataHora = dataHora;
		this.valor = valor;
		this.tipo = tipo;
	}

	
	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public TipoOperacao getTipo() {
		return tipo;
	}
}
