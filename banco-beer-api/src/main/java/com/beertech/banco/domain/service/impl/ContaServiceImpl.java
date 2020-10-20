package com.beertech.banco.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.Profile;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.domain.service.ProfileService;

public class ContaServiceImpl implements ContaService {

	private final ContaRepository contaRepository;
	@Autowired
	private ProfileService profileService;
		
	public ContaServiceImpl(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	@Override
	public Conta criarConta(Conta conta) {
		Optional<Conta> findByHash = contaRepository.findByHash(conta.getHash());
		if(findByHash.isPresent())
			throw new ContaException("Ja existe uma conta com esse valor de Hash");
		Optional<Profile> profileUser = profileService.findByName("USER");
		if(!profileUser.isPresent()) {
			throw new ContaException("Não existe um profile com nome USER");
		}		
		conta.addProfile(profileUser.get());
		return contaRepository.save(conta);
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
	public void atualizaConta(Conta conta) {
		contaRepository.save(conta);		
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

	@Override
	public List<Conta> listaTodasAsContas() {
		return contaRepository.findAll();
	}

	@Override
	public Conta contaPeloId(Long id) {
		Optional<Conta> findById = contaRepository.findById(id);
		if(!findById.isPresent()) {
			throw new ContaException("O id da conta não existe!");
		}
		return findById.get();
	}

	@Override
	public Conta contaPeloHash(String hash) {
		Optional<Conta> conta = contaRepository.findByHash(hash);
		if(!conta.isPresent()) {
			throw new ContaException("O id da conta não existe!");
		}		
		return conta.get();
	}

	@Override
	public List<Operacao> extrato(String hash) {
		Optional<Conta> conta = contaRepository.findByHash(hash);
		if(!conta.isPresent()) {
			throw new ContaException("O id da conta não existe!");
		}
		
		return conta.get().getOperacoes();
	}

	@Override
	public Conta contaPeloEmail(String email) {
		Optional<Conta> conta = contaRepository.findByEmail(email);
		if(!conta.isPresent()) {
			throw new ContaException("O id da conta não existe!");
		}		
		return conta.get();
	}

}
