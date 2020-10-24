package com.beertech.banco.infrastructure.repository.mysql.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;

@Entity
@Table(name = "operacao")
public class MySqlOperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    private TipoOperacao tipo;
    private String nomeContaDestino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private MySqlConta conta;

    public MySqlOperacao() {
    }

    public MySqlOperacao(Long id, BigDecimal valor, TipoOperacao tipo, LocalDateTime dataHora, String hashContaDestino, MySqlConta conta) {
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.nomeContaDestino = hashContaDestino;
        this.id = id;
        this.conta = conta;
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

    public Long getId() {
        return id;
    }

    public MySqlConta getConta() {
        return conta;
    }

    public String getHashContaDestino() {
        return nomeContaDestino;
    }

    public void setHashContaDestino(String hashContaDestino) {
        this.nomeContaDestino = hashContaDestino;
    }

    public MySqlOperacao fromDomain(Operacao operacao) {
        return new MySqlOperacao(operacao.getId(), operacao.getValor(), operacao.getTipo(), operacao.getDataHora(), operacao.getNomeContaDestino(), new MySqlConta().fromDomain(operacao.getConta()));
    }

    public Operacao toDomain(MySqlOperacao mySqlOperacao) {
        return new Operacao(mySqlOperacao.getDataHora(), mySqlOperacao.getValor(), mySqlOperacao.getTipo(), mySqlOperacao.getHashContaDestino());
    }

}
