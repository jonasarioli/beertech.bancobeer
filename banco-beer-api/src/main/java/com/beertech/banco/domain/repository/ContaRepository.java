package com.beertech.banco.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;

public interface ContaRepository {
    Optional<Conta> findByHash(String hash);

    Conta save(Conta conta);

    Optional<Conta> findByEmail(String email);

    Optional<Conta> findById(Long id);

    Page<Conta> findAll(Pageable page);

    Page<Operacao> getExtratoByHash(String hash, Pageable page);

}
