package com.beertech.banco.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.beertech.banco.domain.exception.ContaException;

public class Conta {

	private Long id;
	private String hash;
	private List<Operacao> operacoes;
	private BigDecimal saldo;

	public Conta() {
	}

	public Conta(String hash) {
		this.hash = hash;
		this.operacoes = new ArrayList<Operacao>();
		saldo = new BigDecimal(0);
	}

	public Conta(Long id, String hash, List<Operacao> operacoes, BigDecimal saldo) {
		this.id = id;
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
	}

	public String getHash() {
		return hash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Operacao> getOperacoes() {
		return Collections.unmodifiableList(operacoes);
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void deposito(BigDecimal valor) {
		if (valor.compareTo(new BigDecimal(0)) <= 0)
			throw new ContaException("O valor para depósito deve ser maior do que 0!");
		this.saldo = saldo.add(valor);
	}

	public void saque(BigDecimal valor) {
		if (valor.compareTo(new BigDecimal(0)) <= 0)
			throw new ContaException("O valor para saque deve ser maior do que 0!");
		if (valor.compareTo(this.saldo) >= 0)
			throw new ContaException("O valor para saque não pode ser maior do que o saldo!");
		this.saldo = this.saldo.subtract(valor);
	}

	public void addOperacao(Operacao operacao) {
		this.operacoes.add(operacao);
	}

}
