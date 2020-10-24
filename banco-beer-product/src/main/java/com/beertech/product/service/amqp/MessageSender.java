package com.beertech.product.service.amqp;

import com.beertech.product.model.amqp.RewardMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Value("${amqp.exchange}")
    private String exchangeName;

    @Value("${amqp.routeKey}")
    private String routeKey;

    private AmqpTemplate amqpTemplate;

    @Autowired
    public MessageSender(final AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendRewardMessage(final RewardMessage rewardMessage) {
        amqpTemplate.convertAndSend(exchangeName, routeKey, rewardMessage);
    }
}
