package com.beertech.banco.domain.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import com.beertech.banco.domain.Conta;
import com.beertech.banco.domain.Operacao;
import com.beertech.banco.domain.TipoOperacao;
import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.domain.service.BancoService;

public class BancoServiceImpl implements BancoService {

	private final ContaRepository contaRepository;
		
	public BancoServiceImpl(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	@Override
	public Conta criarConta(Conta conta) {
		Optional<Conta> findByHash = contaRepository.findByHash(conta.getHash());
		if(findByHash.isPresent())
			throw new ContaException("Ja existe uma conta com esse valor de Hash");
		contaRepository.save(conta);
		return conta;
	}

	@Override
	public BigDecimal saldo(String hash) {
		Optional<Conta> conta = contaRepository.findByHash(hash);
		if(!conta.isPresent()) {
			throw new ContaException("O hash da conta não existe!");
		}
		return conta.get().getSaldo();
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
		else if(operacao.getTipo().equals(TipoOperacao.SAQUE))
			conta.saque(operacao.getValor());
		else 
			throw new IllegalArgumentException("Operação não existente!");
		
		conta.addOperacao(operacao);
		contaRepository.save(conta);
		
		return conta;
		
	}

	@Override
	public void atualizaConta(Conta conta) {
		contaRepository.save(conta);		
	}

	@Override
	public void transferencia(String hahsDaContaOrigem, String hahsDaContaDestino, BigDecimal valor) {
		Optional<Conta> contaOrigem = contaRepository.findByHash(hahsDaContaOrigem);
		Optional<Conta> contaDestino = contaRepository.findByHash(hahsDaContaDestino);
		
		if(!contaOrigem.isPresent() || !contaDestino.isPresent()) {
			throw new ContaException("Conta origem e/ou conta destino invalida!");
		}
		
		contaOrigem.get().saque(valor);
		contaDestino.get().deposito(valor);
		
		contaRepository.save(contaOrigem.get());
		contaRepository.save(contaDestino.get());
	}

}
