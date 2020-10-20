package com.beertech.banco.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;

class ContaTest {

	@Test
	void depositoComSucesso() {
		Conta conta = new Conta();
		conta.deposito(new BigDecimal("1000.00"));
		assertEquals(new BigDecimal("1000.00"), conta.getSaldo());
	}
	
	@Test
	void depositoComValorInvalido() {
		Conta conta = new Conta();
		assertThrows(ContaException.class, ()-> {conta.deposito(new BigDecimal("-1000.00"));});		
	}
	
	@Test
	void saqueComSucesso() {
		Conta conta = new Conta();
		conta.deposito(new BigDecimal(1000.00));		
		conta.saque(new BigDecimal(1000.00));
		assertEquals(new BigDecimal(0.00), conta.getSaldo());		
	}
	
	@Test
	void saqueComValorInvalido() {
		Conta conta = new Conta();
		conta.deposito(new BigDecimal(1000.00));
		assertThrows(ContaException.class, ()-> {conta.saque(new BigDecimal("-1000.00"));});		
	}
	
	@Test
	void saqueDeValorMaiorDoQueOSaldo() {
		Conta conta = new Conta();
		assertThrows(ContaException.class, ()-> {conta.saque(new BigDecimal("1000.00"));});		
	}

}
