package com.beertech.banco.infrastructure.repository.mysql.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;

@Embeddable
public class MySqlOperacao {

	private LocalDateTime dataHora;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoOperacao tipo;
	private String hashContaDestino;

	public MySqlOperacao() {
	}

	public MySqlOperacao(BigDecimal valor, TipoOperacao tipo, LocalDateTime dataHora, String hashContaDestino) {
		this.valor = valor;
		this.tipo = tipo;
		this.dataHora = dataHora;
		this.hashContaDestino = hashContaDestino;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoOperacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacao tipo) {
		this.tipo = tipo;
	}

	public String getHashContaDestino() { return hashContaDestino; }

	public void setHashContaDestino(String hashContaDestino) { this.hashContaDestino = hashContaDestino; }

	public MySqlOperacao fromDomain(Operacao operacao) {
		return new MySqlOperacao(operacao.getValor(), operacao.getTipo(), operacao.getDataHora(), operacao.getHashContaDestino());
	}

	public Operacao toDomain(MySqlOperacao mySqlOperacao) {
		return new Operacao(mySqlOperacao.getDataHora(), mySqlOperacao.getValor(), mySqlOperacao.getTipo(), mySqlOperacao.getHashContaDestino());
	}

}
