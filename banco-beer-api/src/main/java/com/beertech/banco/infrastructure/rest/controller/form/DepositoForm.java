package com.beertech.banco.infrastructure.rest.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DepositoForm {
	
	@NotNull
	@Min(value = 1, message = "Valor deve ser maior do que 0!")
	private BigDecimal valor;
	@NotBlank
	private String hashDaConta;
	public DepositoForm(@NotNull @Min(value = 0, message = "Valor deve ser maior do que 0!") BigDecimal valor,
			@NotBlank String hashDaConta) {
		this.valor = valor;
		this.hashDaConta = hashDaConta;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public String getHashDaConta() {
		return hashDaConta;
	}
}
