package com.beertech.banco.infrastructure.rest.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

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

	public static List<OperacaoDto> converter(List<Operacao> operacoes) {
		return operacoes.stream().map(OperacaoDto::new).collect(Collectors.toList());
	}
	
	

}
