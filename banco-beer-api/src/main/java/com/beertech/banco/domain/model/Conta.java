package com.beertech.banco.domain.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.infrastructure.rest.controller.form.ContaForm;

public class Conta {

	private Long id;
	private String hash;
	private List<Operacao> operacoes;
	private BigDecimal saldo;
	private String nome;
	private String email;
	private String cnpj;
	private String senha;
	private EPerfil perfil;
	private Set<Profile> profiles;


	public Conta() {
		this.operacoes = new ArrayList<Operacao>();
		saldo = new BigDecimal(0.00);
		this.hash = getHashMd5(email + cnpj);
	}

	public Conta(Long id, String hash, BigDecimal saldo, List<Operacao> operacoes, String nome, String email, String cnpj,
				 String senha, Set<Profile> profiles) {
		this.id = id;
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
		this.nome = nome;
		this.email = email;
		this.cnpj = cnpj;
		this.senha = senha;
		this.profiles = profiles;
	}
	
	public Conta(ContaForm form) {
		this.nome = form.getNome();
		this.cnpj = form.getCnpj();
		this.email = form.getEmail();
		this.senha = new BCryptPasswordEncoder().encode(form.getSenha());
		this.operacoes = new ArrayList<Operacao>();
		saldo = new BigDecimal(0.00);
		this.hash = getHashMd5(email + cnpj);
	}
	
	public Set<Profile> getProfiles() {
		return profiles;
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
		if (this.saldo.compareTo(valor) < 0)
			throw new ContaException("O valor para saque não pode ser maior do que o saldo!");
		this.saldo = this.saldo.subtract(valor);
	}

	public static String getHashMd5(String value) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
		return hash.toString(16);
	}

	public void addOperacao(Operacao operacao) {
		this.operacoes.add(operacao);
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

	public EPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(EPerfil perfil) {
		this.perfil = perfil;
	}

	public void addProfile(Profile profile) {
		if(this.profiles == null)
			this.profiles = new HashSet<>();
		this.profiles.add(profile);
	}
}
