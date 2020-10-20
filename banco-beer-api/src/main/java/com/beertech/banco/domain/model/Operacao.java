package com.beertech.banco.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


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

	
	
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setTipo(TipoOperacao tipo) {
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
