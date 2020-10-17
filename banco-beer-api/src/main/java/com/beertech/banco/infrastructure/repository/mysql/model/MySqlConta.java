package com.beertech.banco.infrastructure.repository.mysql.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.beertech.banco.domain.Conta;

@Entity
@Table(	name = "conta", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "hash"),
	@UniqueConstraint(columnNames = "email") 
})
public class MySqlConta implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String hash;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "operacoes", joinColumns = @JoinColumn(name = "conta_id"))
	@Column(name = "operacao")
	private List<MySqlOperacao> operacoes;
	@Column(name = "nome")
	@NotBlank
	private String nome;
	@Column(name = "email")
	@NotBlank
	private String email;
	@Column(name = "cnpj")
	@NotBlank
	private String cnpj;
	@Column(name = "senha")
	@NotBlank
	private String senha;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "CONTA_PROFILES", joinColumns = {
            @JoinColumn(name = "CONTA_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "PROFILE_ID") })
	private List<MySqlProfile> profiles;
	
	public MySqlConta() {
	}
	
	public MySqlConta(Long id, String hash, List<MySqlOperacao> operacoes, BigDecimal saldo, String nome, String email, String cnpj,
					  String senha, List<MySqlProfile> perfil) {
		this.id = id;
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
		this.nome = nome;
		this.email = email;
		this.cnpj = cnpj;
		this.senha = senha;
		this.profiles = perfil;
	}
	
	public MySqlConta(String hash, List<MySqlOperacao> operacoes, BigDecimal saldo, String nome, String email, String cnpj,
					  String senha, List<MySqlProfile> perfil) {
		this.hash = hash;
		this.operacoes = operacoes;
		this.saldo = saldo;
		this.nome = nome;
		this.email = email;
		this.cnpj = cnpj;
		this.senha = senha;
		this.profiles = perfil;
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
			return new MySqlConta(conta.getId()
					, conta.getHash()
					, conta.getOperacoes().stream().map(new MySqlOperacao()::fromDomain).collect(Collectors.toList())
					, conta.getSaldo()
					, conta.getNome()
					, conta.getEmail()
					, conta.getCnpj()
					, conta.getSenha()
					, conta.getProfiles().stream().map(new MySqlProfile()::fromDomain).collect(Collectors.toList()));
		else 
			return new MySqlConta(conta.getId()
					,conta.getHash()
					, conta.getOperacoes().stream().map(new MySqlOperacao()::fromDomain).collect(Collectors.toList())
					, conta.getSaldo()
					, conta.getNome()
					, conta.getEmail()
					, conta.getCnpj()
					, conta.getSenha()
					, conta.getProfiles().stream().map(new MySqlProfile()::fromDomain).collect(Collectors.toList()));
	}
	
	public Conta toDomain(MySqlConta mySqlConta) {
		return new Conta( mySqlConta.getId()
				, mySqlConta.getHash()
				, mySqlConta.getOperacoes().stream().map(new MySqlOperacao()::toDomain).collect(Collectors.toList())
				, mySqlConta.getSaldo()
				, mySqlConta.getNome()
				, mySqlConta.getEmail()
				, mySqlConta.getCnpj()
				, mySqlConta.getSenha()
				, mySqlConta.getProfiles().stream().map(new MySqlProfile()::toDomain).collect(Collectors.toList()));
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

	public List<MySqlProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<MySqlProfile> profiles) {
		this.profiles = profiles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
