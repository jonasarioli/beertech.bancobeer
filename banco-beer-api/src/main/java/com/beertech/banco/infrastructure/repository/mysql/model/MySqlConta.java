package com.beertech.banco.infrastructure.repository.mysql.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.beertech.banco.domain.Conta;

@Entity
@Table(name = "conta")
public class MySqlConta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String hash;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "operacoes", joinColumns = @JoinColumn(name = "conta_id"))
	@Column(name = "operacao")
	private List<MySqlOperacao> operacoes;
	
	public MySqlConta() {
	}
	
	public MySqlConta(Long id, String hash, List<MySqlOperacao> operacoes, BigDecimal saldo) {
		this.id = id;
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
	}
	
	public MySqlConta(String hash, List<MySqlOperacao> operacoes, BigDecimal saldo) {
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<MySqlOperacao> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<MySqlOperacao> operacoes) {
		this.operacoes = operacoes;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	private BigDecimal saldo;

	public MySqlConta fromDomain(Conta conta) {
		if(conta.getId() != null)
			return new MySqlConta(conta.getId(), conta.getHash(), conta.getOperacoes().stream().map(new MySqlOperacao()::fromDomain).collect(Collectors.toList()), conta.getSaldo());
		else 
			return new MySqlConta(conta.getHash(), conta.getOperacoes().stream().map(new MySqlOperacao()::fromDomain).collect(Collectors.toList()), conta.getSaldo());
	}
	
	public Conta toDomain(MySqlConta mySqlConta) {
		return new Conta(mySqlConta.getId(), mySqlConta.hash, mySqlConta.getOperacoes().stream().map(new MySqlOperacao()::toDomain).collect(Collectors.toList()), mySqlConta.getSaldo());
	}
}
