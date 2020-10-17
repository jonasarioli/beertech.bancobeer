package com.beertech.banco.infrastructure.rest.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.beertech.banco.domain.TipoOperacao;

public class OperacaoDto {

	@NotNull
	@Min(value = 0, message = "Valor deve ser maior do que 0!")
	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
