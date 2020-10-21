package com.beertech.banco.infrastructure.amqp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;
import com.beertech.banco.infrastructure.amqp.sender.MessageSender;
import com.beertech.banco.infrastructure.amqp.service.RelayService;

@Service
public class RelayServiceImpl implements RelayService {

	private final MessageSender messageSender;
	private final ContaRepository contaRepository;

	@Autowired
	public RelayServiceImpl(MessageSender messageSender, ContaRepository contaRepository) {
		this.messageSender = messageSender;
		this.contaRepository = contaRepository;
	}

	@Override
	public void transfer(TransferenciaMessage transferenciaMessage) {
		Optional<Conta> contaOrigem = contaRepository.findByHash(transferenciaMessage.getContaOrigem());
		Optional<Conta> contaDestino = contaRepository.findByHash(transferenciaMessage.getContaDestino());
		if ((contaOrigem.isPresent() && contaOrigem.get().getProfiles().iterator().next().getName().equals("ROLE_ADMIN"))
				|| (contaDestino.isPresent() && contaDestino.get().getProfiles().iterator().next().getName().equals("ROLE_ADMIN"))) {
			throw new ContaException("Não pode haver movimentação em conta de ADMIN");
		}
		transferenciaMessage.setTipo("TRANSFERENCIA");
		messageSender.sendTransferMessage(transferenciaMessage);
	}

	@Override
	public void operation(OperacaoMessage operacaoMessage) {
		Optional<Conta> conta = contaRepository.findByHash(operacaoMessage.getHash());
		if (conta.isPresent() && conta.get().getProfiles().iterator().next().getName().equals("ROLE_ADMIN")) {
			throw new ContaException("Não pode haver movimentação em conta de ADMIN");
		}
		messageSender.sendOperationMessage(operacaoMessage);
	}

}
