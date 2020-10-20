package com.beertech.banco.infrastructure.rest.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.beertech.banco.domain.model.Operacao;

public class OperacaoDto {
	
	private String tipo;
	private BigDecimal valor;
	private LocalDateTime dataHora;
	
	public OperacaoDto(Operacao operacao) {
		this.tipo = operacao.getTipo().name();
		this.valor = operacao.getValor();
		this.dataHora = operacao.getDataHora();
	}

	public String getTipo() {
		return tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}
	
	

}
