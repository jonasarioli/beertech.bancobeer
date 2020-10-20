package com.beertech.banco.infrastructure.amqp.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class ContaListener {
	@RabbitListener(queues = "${amqp.queue}")
	public void consumer(Message message) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		String json = new String(message.getBody());
		
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		if(jsonObject.get("tipo").getAsString().equals("TRANSFERENCIA")) {	
			TransferenciaMessage transferenciaMessage = objectMapper.readValue(json, TransferenciaMessage.class);
			
			System.out.println(transferenciaMessage);
		} else {
			OperacaoMessage operacaoMessage = objectMapper.readValue(json, OperacaoMessage.class);
			System.out.println(operacaoMessage);
		}
	}
}