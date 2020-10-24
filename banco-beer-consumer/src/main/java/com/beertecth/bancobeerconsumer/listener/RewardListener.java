package com.beertecth.bancobeerconsumer.listener;

import com.beertecth.bancobeerconsumer.client.ContaClient;
import com.beertecth.bancobeerconsumer.config.RabbitConfig;
import com.beertecth.bancobeerconsumer.model.OperacaoMessage;
import com.beertecth.bancobeerconsumer.model.RewardMessage;
import com.beertecth.bancobeerconsumer.model.TransferenciaDto;
import com.beertecth.bancobeerconsumer.model.TransferenciaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RewardListener {
    @Autowired
    ContaClient client;

    @RabbitListener(queues = RabbitConfig.REWARD_QUEUE)
    public void consumer(Message message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        String json = new String(message.getBody());

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        RewardMessage rewardMessage = objectMapper.readValue(json, RewardMessage.class);

        System.out.println(rewardMessage);

        try {
            client.reward(rewardMessage);
        }catch (Exception exception){
            System.out.println("RewardListener.exception: " +  exception.getMessage());
        }
    }
}
