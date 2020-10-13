package com.beertecth.bancobeerconsumer.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TransferenciaMessage {

	@JsonProperty(required=false,value="tipo")
	private String tipo;
	@JsonProperty(required=false,value="origem")
	private String contaOrigem;
	@JsonProperty(required=false,value="valor")
	private BigDecimal valor;
	@JsonProperty(required=false,value="destino")
	private String contaDestino;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	
	
}
