package com.beertech.banco.infrastructure.repository.mysql.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlOperacao;

@Repository
public class MySqlContaRepositoryImpl implements ContaRepository {

	private com.beertech.banco.infrastructure.repository.mysql.ContaRepository contaRepository;

	@Autowired
	public MySqlContaRepositoryImpl(
			com.beertech.banco.infrastructure.repository.mysql.ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	@Override
	public Optional<Conta> findByHash(String hash) {
		return contaRepository.findByHash(hash).map(new MySqlConta()::toDomain);
	}

	@Override
	public Conta save(Conta conta) {
		MySqlConta save = contaRepository.save(new MySqlConta().fromDomain(conta));
		return new MySqlConta().toDomain(save);
	}

	@Override
	public Optional<Conta> findByEmail(String email) {
		return contaRepository.findByEmail(email).map(new MySqlConta()::toDomain);
	}

	@Override
	public Optional<Conta> findById(Long id) {
		return contaRepository.findById(id).map(new MySqlConta()::toDomain);
	}

	@Override
	public Page<Conta> findAll(Pageable page) {
		Page<MySqlConta> findAll = contaRepository.findAll(page);
		return findAll.map(new MySqlConta()::toDomain);
	}

	@Override
	public Page<Operacao> getExtratoByHash(String hash, Pageable page) {
		Page<MySqlOperacao> findOperacoesByHash = contaRepository.findOperacoesByHash(hash, page);
		return findOperacoesByHash.map(new MySqlOperacao()::toDomain);
	}
}
