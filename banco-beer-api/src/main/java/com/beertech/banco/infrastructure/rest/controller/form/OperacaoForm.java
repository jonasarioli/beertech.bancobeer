package com.beertech.banco.infrastructure.rest.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.beertech.banco.domain.model.TipoOperacao;

public class OperacaoForm {

	@NotNull
	private TipoOperacao tipo;
	@NotNull
	private BigDecimal valor;
	@NotBlank
	private String hash;
	public TipoOperacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoOperacao tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
}
