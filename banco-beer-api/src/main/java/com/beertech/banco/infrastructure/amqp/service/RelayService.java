package com.beertech.banco.infrastructure.amqp.service;

import com.beertech.banco.infrastructure.amqp.model.OperacaoMessage;
import com.beertech.banco.infrastructure.amqp.model.TransferenciaMessage;

public interface RelayService {

    public void transfer(TransferenciaMessage transferenciaMessage);

    public void operation(OperacaoMessage operacaoMessage);
}
