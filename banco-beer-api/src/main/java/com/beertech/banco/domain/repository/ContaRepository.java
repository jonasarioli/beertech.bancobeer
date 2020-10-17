package com.beertech.banco.domain.repository;

import java.util.Optional;

import com.beertech.banco.domain.Conta;

public interface ContaRepository {
	Optional<Conta> findByHash(String hash);
	Conta save(Conta conta);
	Optional<Conta> findByEmail(String email);
	Optional<Conta> findById(Long id);
	
}
