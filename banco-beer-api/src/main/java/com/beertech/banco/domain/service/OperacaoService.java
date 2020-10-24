package com.beertech.banco.domain.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;

public interface OperacaoService {
    Conta realizaOperacao(String contaHash, Operacao operacao);

    void transferencia(String hahsDaContaOrigem, String hahsDaContaDestino, BigDecimal valor);

    Page<Operacao> extrato(Long contaId, Pageable page);

    Page<Operacao> extratoPorTipo(Long contaId, TipoOperacao tipo, Pageable page);

    Conta realizaResgate(String hash, String nomeProduto, BigDecimal valor);

    Operacao save(Operacao operacao);
}
