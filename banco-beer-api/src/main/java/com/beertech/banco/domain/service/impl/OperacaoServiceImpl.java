package com.beertech.banco.domain.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.domain.service.OperacaoService;

public class OperacaoServiceImpl implements OperacaoService {
	
	private final ContaRepository contaRepository;
	
	public OperacaoServiceImpl(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	@Override
	public Conta realizaOperacao(String contaHash, Operacao operacao) {
		Optional<Conta> contaByHash = contaRepository.findByHash(contaHash);
		if(!contaByHash.isPresent()) {
			throw new ContaException("O hash da conta não existe!");
		}
		Conta conta = contaByHash.get();
		if(operacao.getTipo().equals(TipoOperacao.DEPOSITO))
			conta.deposito(operacao.getValor());
		else if(operacao.getTipo().equals(TipoOperacao.SAQUE)) {
			conta.saque(operacao.getValor());
			operacao.setValor(operacao.getValor().multiply(new BigDecimal(-1)));			
		}
		else 
			throw new IllegalArgumentException("Operação não existente!");
		
		conta.addOperacao(operacao);
		contaRepository.save(conta);		
		return conta;		
	}
	
	@Override
	public void transferencia(String hahsDaContaOrigem, String hahsDaContaDestino, BigDecimal valor) {
		Optional<Conta> contaOrigem = contaRepository.findByHash(hahsDaContaOrigem);
		Optional<Conta> contaDestino = contaRepository.findByHash(hahsDaContaDestino);
		
		if(!contaOrigem.isPresent() || !contaDestino.isPresent())
			throw new ContaException("Conta origem e/ou conta destino invalida!");
		
		if(valor.compareTo(new BigDecimal(0)) <= 0)
			throw new ContaException("O valor de transferencia deve ser maior que zero!");
		
		if(contaOrigem.get().getSaldo().compareTo(valor) < 0)
			throw new ContaException("O valor de transferencia deve ser menor ou igual ao saldo da conta de origem!");
		
		contaOrigem.get().saque(valor);
		contaDestino.get().deposito(valor);
		
		contaOrigem.get().addOperacao(new Operacao(valor.multiply(new BigDecimal(-1)), TipoOperacao.TRANSFERENCIA));
		contaDestino.get().addOperacao(new Operacao(valor, TipoOperacao.TRANSFERENCIA));
		
		contaRepository.save(contaOrigem.get());
		contaRepository.save(contaDestino.get());
	}
}
