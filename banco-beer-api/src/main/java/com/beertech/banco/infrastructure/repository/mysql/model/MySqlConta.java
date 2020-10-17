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
import com.beertech.banco.domain.Perfil;

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
	@Column(name = "nome")
	private String nome;
	@Column(name = "email")
	private String email;
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "senha")
	private String senha;
	@Column(name = "perfil")
	private Perfil perfil;
	
	public MySqlConta() {
	}
	
	public MySqlConta(Long id, String hash, List<MySqlOperacao> operacoes, BigDecimal saldo, String nome, String email, String cnpj,
					  String senha, Perfil perfil) {
		this.id = id;
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
		this.nome = nome;
		this.email = email;
		this.cnpj = cnpj;
		this.senha = senha;
		this.perfil = perfil;
	}
	
	public MySqlConta(String hash, List<MySqlOperacao> operacoes, BigDecimal saldo, String nome, String email, String cnpj,
					  String senha, Perfil perfil) {
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
		this.nome = nome;
		this.email = email;
		this.cnpj = cnpj;
		this.senha = senha;
		this.perfil = perfil;
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
			return new MySqlConta(conta.getId(), conta.getHash(), conta.getOperacoes().stream().map(new MySqlOperacao()::fromDomain).collect(Collectors.toList()),
					conta.getSaldo(), conta.getNome(), conta.getEmail(), conta.getCnpj(), conta.getSenha(), conta.getPerfil());
		else 
			return new MySqlConta(conta.getId(),conta.getHash(), conta.getOperacoes().stream().map(new MySqlOperacao()::fromDomain).collect(Collectors.toList()), conta.getSaldo(), conta.getNome(), conta.getEmail(), conta.getCnpj(), conta.getSenha(), conta.getPerfil());
	}
	
	public Conta toDomain(MySqlConta mySqlConta) {
		return new Conta( mySqlConta.getId(), mySqlConta.getHash(), mySqlConta.getOperacoes().stream().map(new MySqlOperacao()::toDomain).collect(Collectors.toList()),
				mySqlConta.getSaldo(), mySqlConta.getNome() , mySqlConta.getEmail(), mySqlConta.getCnpj(), mySqlConta.getSenha(), mySqlConta.getPerfil());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
