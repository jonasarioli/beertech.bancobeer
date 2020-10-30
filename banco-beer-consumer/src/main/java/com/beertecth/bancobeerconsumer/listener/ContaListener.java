package com.beertecth.bancobeerconsumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beertecth.bancobeerconsumer.client.ContaClient;
import com.beertecth.bancobeerconsumer.config.RabbitConfig;
import com.beertecth.bancobeerconsumer.model.OperacaoMessage;
import com.beertecth.bancobeerconsumer.model.TransferenciaDto;
import com.beertecth.bancobeerconsumer.model.TransferenciaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Slf4j
@Component
public class ContaListener {

	@Autowired
	ContaClient client;

	@RabbitListener(queues = RabbitConfig.CONTA_QUEUE)
	public void consumer(Message message) throws JsonProcessingException {
		log.info("conta listener -- message = {}", message);

		System.out.println("conta listener -- message: " + message);

		ObjectMapper objectMapper = new ObjectMapper();

		String json = new String(message.getBody());

		try{
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
			if(jsonObject.get("tipo").getAsString().equals("TRANSFERENCIA")) {
				TransferenciaMessage transferenciaMessage = objectMapper.readValue(json, TransferenciaMessage.class);

				client.transferencia(new TransferenciaDto(transferenciaMessage));
			} else {
				OperacaoMessage operacaoMessage = objectMapper.readValue(json, OperacaoMessage.class);
				client.sendOperation(operacaoMessage);
			}}catch (Exception exception){
			System.out.println("conta listener -- exception: " + exception);
		}
	}
}