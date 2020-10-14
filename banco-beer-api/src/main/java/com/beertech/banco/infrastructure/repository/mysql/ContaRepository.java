package com.beertech.banco.infrastructure.repository.mysql;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;

public interface ContaRepository extends CrudRepository<MySqlConta, Long> {

	Optional<MySqlConta> findByHash(String hash);
	
}
