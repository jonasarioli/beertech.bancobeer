package com.beertech.banco.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.repository.ContaRepository;

class BancoServiceImplTest {

	private ContaRepository contaRepository;
	private BancoServiceImpl tested;
	
	@BeforeEach
    void setUp() {
		contaRepository = mock(ContaRepository.class);
		tested = new BancoServiceImpl(contaRepository);
    }
	

    @Test
    void criarUmaContaComSucesso() {
        final Conta conta = new Conta("hash");
 
        final Conta id = tested.criarConta(conta);
 
        verify(contaRepository).save(any(Conta.class));
        assertNotNull(id);
    }
    
    @Test
    void retornaSaldoComSucesso() {
    	final Conta contaComSaldo = new Conta("hashValue");
    	contaComSaldo.deposito(new BigDecimal(100));
    	contaComSaldo.saque(new BigDecimal(10));
    	when(contaRepository.findByHash("hashValue")).thenReturn(Optional.of(contaComSaldo));
    	assertEquals(new BigDecimal(90), tested.saldo("hashValue"));
    }

}
