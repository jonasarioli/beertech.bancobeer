package com.beertech.banco.domain.service;

import java.math.BigDecimal;
import java.util.List;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.EPerfil;

public interface ContaService {
	Conta criarConta(Conta conta, EPerfil perfil);
	BigDecimal saldo(String hash);	
	void atualizaConta(Conta conta);	
	List<Conta> listaTodasAsContas();
	Conta contaPeloId(Long id);
	Conta contaPeloHash(String hash);
	List<Operacao> extrato(String hash);
	Conta contaPeloEmail(String email);
}
