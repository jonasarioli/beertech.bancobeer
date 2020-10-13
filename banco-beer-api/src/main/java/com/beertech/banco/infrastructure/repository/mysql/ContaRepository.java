package com.beertech.banco.infrastructure.repository.mysql;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.beertech.banco.domain.Conta;

public interface ContaRepository extends CrudRepository<Conta, Long> {

	Optional<Conta> findByHash(String hash);
	
}
