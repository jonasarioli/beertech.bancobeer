package com.beertech.banco.infrastructure.rest.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransferenciaForm {

	@NotBlank
	private String hashContaDestino;
	@NotNull
	@Min(value = 0, message = "Valor deve ser maior do que 0!")
	private BigDecimal valor;
	
	public String getHashContaDestino() {
		return hashContaDestino;
	}
	public BigDecimal getValor() {
		return valor;
	}
	
	
	
	
	
}
