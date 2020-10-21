package com.beertech.banco.infrastructure.amqp.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class OperacaoMessage {
	@JsonProperty(value = "tipo")
	@ApiModelProperty(example = "DEPOSITO")
	private String tipo;

	@JsonProperty(value = "valor")
	@ApiModelProperty(example = "12.5")
	private BigDecimal valor;

	@JsonProperty(value = "hash")
	@ApiModelProperty(example = "123456")
	private String hash;
	
	public OperacaoMessage() {
	}

	public OperacaoMessage(String tipo, BigDecimal valor, String hash) {
		this.tipo = tipo;
		this.valor = valor;
		this.hash = hash;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getTipo() {
		return tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public String toString() {
		return "OperacaoMessage [tipo=" + tipo + ", valor=" + valor + ", hash=" + hash + "]";
	}
}
