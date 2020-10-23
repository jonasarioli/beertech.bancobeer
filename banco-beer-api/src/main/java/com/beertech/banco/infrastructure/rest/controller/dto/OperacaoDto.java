package com.beertech.banco.infrastructure.rest.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.beertech.banco.domain.model.Operacao;

public class OperacaoDto {
	
	private String tipo;
	private BigDecimal valor;
	private LocalDateTime dataHora;
	private String nomeContaOrigemOuDestino;
	
	public OperacaoDto(Operacao operacao) {
		this.tipo = operacao.getTipo().name();
		this.valor = operacao.getValor();
		this.dataHora = operacao.getDataHora();
		this.nomeContaOrigemOuDestino = operacao.getConta().getNome();
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

	public String getNomeContaOrigemOuDestino() {
		return nomeContaOrigemOuDestino;
	}

	public static Page<OperacaoDto> converter(Page<Operacao> operacoes) {
		return operacoes.map(OperacaoDto::new);
	}
}
