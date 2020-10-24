package com.beertech.banco.infrastructure.amqp.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;

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

    public void sendOperationMessage(final OperacaoMessage operacaoMessage) {
        amqpTemplate.convertAndSend(exchangeName, routeKey, operacaoMessage);
    }

    public void sendTransferMessage(final TransferenciaMessage transferenciaMessage) {
        amqpTemplate.convertAndSend(exchangeName, routeKey, transferenciaMessage);
    }
}
