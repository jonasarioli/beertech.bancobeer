package com.beertech.banco.infrastructure.repository.mysql.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;

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
    return new MySqlConta().toDomain((contaRepository.save(new MySqlConta().fromDomain(conta))));
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
  public List<Conta> findAll() {
    Iterable<MySqlConta> findAll = contaRepository.findAll();
    return StreamSupport.stream(findAll.spliterator(), false)
        .map(new MySqlConta()::toDomain)
        .collect(Collectors.toList());
  }
}
