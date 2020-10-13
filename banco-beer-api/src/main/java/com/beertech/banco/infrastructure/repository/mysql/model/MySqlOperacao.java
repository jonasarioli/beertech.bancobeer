package com.beertech.banco.infrastructure.repository.mysql.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beertech.banco.domain.Operacao;
import com.beertech.banco.domain.TipoOperacao;

@Entity
@Table(name = "operaco")
public class MySqlOperacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime dataHora;
	private BigDecimal valor;
	private TipoOperacao tipo;
	
	@ManyToOne
    @JoinColumn(name = "conta_operacao_id")
	private MySqlConta conta;
	
	
	public MySqlOperacao() {
	}
	
	public MySqlOperacao(MySqlConta conta, BigDecimal valor, TipoOperacao tipo, LocalDateTime dataHora) {
		this.conta = conta;
		this.valor = valor;
		this.tipo = tipo;
		this.dataHora = dataHora;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public MySqlConta getConta() {
		return conta;
	}
	public void setConta(MySqlConta conta) {
		this.conta = conta;
	}
	
	public MySqlOperacao fromDomain(Operacao operacao) {
		return new MySqlOperacao(new MySqlConta().fromDomain(operacao.getConta()), operacao.getValor(), operacao.getTipo(), operacao.getDataHora());
	}
	
	public Operacao toDomain(MySqlOperacao mySqlOperacao) {
		return new Operacao(new MySqlConta().toDomain(mySqlOperacao.getConta()), mySqlOperacao.getDataHora(), mySqlOperacao.getValor(), mySqlOperacao.getTipo());
	}
	
}
