package com.beertech.banco.domain.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.repository.ContaRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

}
