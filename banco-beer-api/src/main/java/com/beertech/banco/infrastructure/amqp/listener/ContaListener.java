package com.beertech.banco.infrastructure.amqp.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.domain.service.OperacaoService;
import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class ContaListener {

	@Autowired
	OperacaoService operacaoService;
	
	@RabbitListener(queues = "${amqp.queue}")
	public void consumer(Message message) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		String json = new String(message.getBody());

		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		if (jsonObject.get("tipo").getAsString().equals("TRANSFERENCIA")) {
			TransferenciaMessage transferenciaMessage = objectMapper.readValue(json, TransferenciaMessage.class);
			operacaoService.transferencia(transferenciaMessage.getContaOrigem(), transferenciaMessage.getContaDestino(), transferenciaMessage.getValor());
		} else {
			OperacaoMessage operacaoMessage = objectMapper.readValue(json, OperacaoMessage.class);
			Operacao operacao = new Operacao(operacaoMessage.getValor(), TipoOperacao.valueOf(operacaoMessage.getTipo()));
			operacaoService.realizaOperacao(operacaoMessage.getHash(), operacao);
		}
	}
}