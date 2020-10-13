package com.beertech.banco.domain.service;

import java.math.BigDecimal;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.Operacao;

public interface BancoService {
	Conta criarConta(Conta conta);
	BigDecimal saldo(String hash);
	Conta realizaOperacao(String contaHash, Operacao operacao);
	void atualizaConta(Conta conta);
	void transferencia(String hahsDaContaOrigem, String hahsDaContaDestino, BigDecimal valor);
	
}
