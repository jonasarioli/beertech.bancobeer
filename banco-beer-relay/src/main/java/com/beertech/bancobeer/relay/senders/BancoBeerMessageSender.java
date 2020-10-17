package com.beertech.bancobeer.relay.senders;

import com.beertech.bancobeer.relay.vos.OperacaoMessage;
import com.beertech.bancobeer.relay.vos.TransferenciaMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BancoBeerMessageSender {

  @Value("${amqp.exchange}")
  private String exchangeName;

  @Value("${amqp.routeKey}")
  private String routeKey;

  private AmqpTemplate amqpTemplate;

  @Autowired
  public BancoBeerMessageSender(final AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  public void sendOperationMessage(final OperacaoMessage operacaoMessage) {
    amqpTemplate.convertAndSend(exchangeName, routeKey, operacaoMessage);
  }

  public void sendTransferMessage(final TransferenciaMessage transferenciaMessage) {
    amqpTemplate.convertAndSend(exchangeName, routeKey, transferenciaMessage);
  }
}
