package com.beertech.banco.domain.service;

import java.math.BigDecimal;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;

public interface OperacaoService {
	Conta realizaOperacao(String contaHash, Operacao operacao);
	void transferencia(String hahsDaContaOrigem, String hahsDaContaDestino, BigDecimal valor);
}
