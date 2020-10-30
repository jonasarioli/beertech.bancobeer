package com.beertech.banco.infrastructure.repository.mysql.repository.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlConta;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlProfile;

class MySqlContaRepositoryImplTest {

    private com.beertech.banco.infrastructure.repository.mysql.ContaRepository contaRepository;
    MySqlContaRepositoryImpl testClass;

    @BeforeEach
    void setUp() {
        contaRepository = mock(com.beertech.banco.infrastructure.repository.mysql.ContaRepository.class);
        testClass = new MySqlContaRepositoryImpl(contaRepository);
    }

    @Test
    void findByHashSuccess() {
        when(contaRepository.findByHash("hash")).thenReturn(
                Optional.of(new MySqlConta(1l
                        , "hash"
                        , new BigDecimal(10.0)
                        , "Nome"
                        , "email@email.com"
                        , "cnpj"
                        , "password"
                        , new HashSet<MySqlProfile>())));
        Optional<Conta> findByHash = testClass.findByHash("hash");
        assertNotNull(findByHash.get());
    }

    @Test
    void save() {
        Conta conta = new Conta();
        MySqlConta mySqlConta = new MySqlConta().fromDomain(conta);
        when(contaRepository.save(mySqlConta)).thenReturn(new MySqlConta());
        Conta save = testClass.save(conta);
        assertNotNull(save);
    }
}
