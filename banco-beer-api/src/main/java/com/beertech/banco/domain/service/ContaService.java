package com.beertech.banco.domain.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.EPerfil;

public interface ContaService {
	Conta criarConta(Conta conta, EPerfil perfil);
	BigDecimal saldo(String hash);	
	void atualizaConta(Conta conta);	
	Page<Conta> listaTodasAsContas(Pageable page);
	Page<Conta> listaTodasAsContasUsuarios(Pageable page);
	Conta contaPeloId(Long id);
	Conta contaPeloHash(String hash);
	
	Conta contaPeloEmail(String email);
}
