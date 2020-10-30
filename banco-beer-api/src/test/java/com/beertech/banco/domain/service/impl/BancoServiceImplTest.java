package com.beertech.banco.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.EPerfil;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.domain.repository.OperacaoRepository;

class BancoServiceImplTest {

    private ContaRepository contaRepository;
    private OperacaoRepository operacaoRepository;
    private ContaServiceImpl tested;
    private OperacaoServiceImpl operacao;

    @BeforeEach
    void setUp() {
        contaRepository = mock(ContaRepository.class);
        operacaoRepository = mock(OperacaoRepository.class);
        tested = new ContaServiceImpl(contaRepository);
        operacao = new OperacaoServiceImpl(contaRepository, operacaoRepository);
    }

    @Test
    void criarUmaContaComSucesso() {
        final Conta conta = new Conta();

        final Conta id = tested.criarConta(conta, EPerfil.USER);

        verify(contaRepository).save(any(Conta.class));
        assertNotNull(id);
    }

    void nãoCriarUmaContaComHahsJaExistente() {
        final Conta conta = new Conta();
        when(contaRepository.findByHash("hash")).thenReturn(Optional.of(new Conta()));
        assertThrows(ContaException.class, () -> {
            tested.criarConta(conta, EPerfil.USER);
        });
    }

    @Test
    void retornaSaldoComSucesso() {
        final Conta contaComSaldo = new Conta();
        contaComSaldo.deposito(new BigDecimal(100));
        contaComSaldo.saque(new BigDecimal(10));
        when(contaRepository.findByHash("hashValue")).thenReturn(Optional.of(contaComSaldo));
        assertEquals(new BigDecimal(90), tested.saldo("hashValue"));
    }

    @Test
    void retornaSaldoComContaInexistente() {
        when(contaRepository.findByHash("hashValue")).thenReturn(Optional.ofNullable(null));
        assertThrows(ContaException.class, () -> {
            tested.saldo("hashValue");
        });
    }

    @Test
    void realizaOperacaoDeposito() {
        final Conta conta = new Conta();
        final Operacao deposito = new Operacao(new BigDecimal("1050.90"), TipoOperacao.DEPOSITO, "hash");
        when(contaRepository.findByHash("hash")).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(new Conta());
        operacao.realizaOperacao("hash", deposito);
        assertEquals(new BigDecimal("1050.90"), conta.getSaldo());
        assertEquals(1, conta.getOperacoes().size());

    }

    @Test
    void realizaOperacaoSaque() {
        final Conta conta = new Conta();
        final Operacao deposito = new Operacao(new BigDecimal("1050.90"), TipoOperacao.DEPOSITO, "hash");
        final Operacao saque = new Operacao(new BigDecimal("50.40"), TipoOperacao.SAQUE, "hash");
        when(contaRepository.findByHash("hash")).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(new Conta());
        operacao.realizaOperacao("hash", deposito);
        operacao.realizaOperacao("hash", saque);
        assertEquals(new BigDecimal("1000.50"), conta.getSaldo());
        assertEquals(2, conta.getOperacoes().size());

    }

    @Test
    void naoRealizaOperacaoDepositoComValorInvalido() {
        final Conta conta = new Conta();
        final Operacao deposito = new Operacao(new BigDecimal("0"), TipoOperacao.DEPOSITO, "hash");
        when(contaRepository.findByHash("hash")).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(new Conta());
        assertThrows(ContaException.class, () -> {
            operacao.realizaOperacao("hash", deposito);
        });

    }

    @Test
    void naoRealizaOperacaoSaqueComSaldoInsuficiente() {
        final Conta conta = new Conta();
        final Operacao saque = new Operacao(new BigDecimal("50.40"), TipoOperacao.SAQUE, "hash");
        when(contaRepository.findByHash("hash")).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(new Conta());
        assertThrows(ContaException.class, () -> {
            operacao.realizaOperacao("hash", saque);
        });

    }

    @Test
    void realizaTransferenciaComSucesso() {
        final Conta contaOrigem = new Conta();
        final Conta contaDestino = new Conta();
        final Operacao deposito = new Operacao(new BigDecimal("50.40"), TipoOperacao.DEPOSITO, "hashDestino");
        when(contaRepository.findByHash("hashOrigem")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByHash("hashDestino")).thenReturn(Optional.of(contaDestino));
        when(contaRepository.save(contaOrigem)).thenReturn(new Conta());
        when(contaRepository.save(contaDestino)).thenReturn(new Conta());
        operacao.realizaOperacao("hashOrigem", deposito);
        operacao.transferencia("hashOrigem", "hashDestino", new BigDecimal("40.40"));
        assertEquals(new BigDecimal("40.40"), contaDestino.getSaldo());
        assertEquals(new BigDecimal("10.00"), contaOrigem.getSaldo());
    }

    @Test
    void naoRealizaTransferenciaComSaldoInsuficiente() {
        final Conta contaOrigem = new Conta();
        final Conta contaDestino = new Conta();
        when(contaRepository.findByHash("hashOrigem")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByHash("hashDestino")).thenReturn(Optional.of(contaDestino));
        when(contaRepository.save(contaOrigem)).thenReturn(new Conta());
        when(contaRepository.save(contaDestino)).thenReturn(new Conta());
        assertThrows(ContaException.class, () -> {
            operacao.transferencia("hashOrigem", "hashDestino", new BigDecimal("40.40"));
        });
    }

    @Test
    void naoRealizaTransferenciaComContaOrigemInvalida() {
        when(contaRepository.findByHash("hashOrigem")).thenReturn(Optional.ofNullable(null));
        assertThrows(ContaException.class, () -> {
            operacao.transferencia("hashOrigem", "hashDestino", new BigDecimal("40.40"));
        });
    }

    @Test
    void naoRealizaTransferenciaComContaDestinoInvalida() {
        final Conta contaOrigem = new Conta();
        when(contaRepository.findByHash("hashOrigem")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByHash("hashDestino")).thenReturn(Optional.ofNullable(null));
        ;
        assertThrows(ContaException.class, () -> {
            operacao.transferencia("hashOrigem", "hashDestino", new BigDecimal("40.40"));
        });
    }

    @Test
    void naoRealizaTransferenciaComValorInvalido() {
        final Conta contaOrigem = new Conta();
        final Conta contaDestino = new Conta();
        when(contaRepository.findByHash("hashOrigem")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByHash("hashDestino")).thenReturn(Optional.of(contaDestino));
        when(contaRepository.save(contaOrigem)).thenReturn(new Conta());
        when(contaRepository.save(contaDestino)).thenReturn(new Conta());
        assertThrows(ContaException.class, () -> {
            operacao.transferencia("hashOrigem", "hashDestino", new BigDecimal("0.00"));
        });
    }

}
