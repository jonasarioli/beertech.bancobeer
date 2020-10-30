package com.beertech.banco.infrastructure.repository.mysql;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlOperacao;

public interface ContaRepository extends JpaRepository<MySqlConta, Long> {
    Page<MySqlConta> findAll(Pageable page);

    Page<MySqlOperacao> findOperacoesByHash(String hash, Pageable page);

    Optional<MySqlConta> findByHash(String hash);

    Optional<MySqlConta> findByEmail(String email);

}
