package com.beertech.banco.infrastructure.amqp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;
import com.beertech.banco.infrastructure.amqp.sender.MessageSender;
import com.beertech.banco.infrastructure.amqp.service.RelayService;

@Service
public class RelayServiceImpl implements RelayService {

	private final MessageSender messageSender;

	  @Autowired
	  public RelayServiceImpl(MessageSender messageSender) {
	    this.messageSender = messageSender;
	  }

	  @Override
	  public void transfer(TransferenciaMessage transferenciaMessage) {
	    transferenciaMessage.setTipo("TRANSFERENCIA");
	    messageSender.sendTransferMessage(transferenciaMessage);
	  }
	  
	  @Override
	  public void operation(OperacaoMessage operacaoMessage) {
	    messageSender.sendOperationMessage(operacaoMessage);
	  }

}
